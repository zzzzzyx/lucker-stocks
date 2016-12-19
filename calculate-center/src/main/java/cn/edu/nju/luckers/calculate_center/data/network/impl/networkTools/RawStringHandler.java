package cn.edu.nju.luckers.calculate_center.data.network.impl.networkTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.nju.luckers.calculate_center.po.StockInformPO;
import cn.edu.nju.luckers.calculate_center.po.StockOneDayPO;


public class RawStringHandler {

	public ArrayList<String> getStockNameList(String str){
		ArrayList<String> list = new ArrayList<String>();
		
		Pattern p = Pattern.compile("(?<=\\\"name\\\": ?\\\")\\w+(?=\\\")");
		Matcher matcher = p.matcher(str);
		
		while(matcher.find()){
			String temp = matcher.group();
			list.add(temp);
		}
		
		return list;
	}

	public StockInformPO handleStock(String jsonContent, String id) {
		
		//第一步：把中括号内的内容提取出来
		Pattern p = Pattern.compile("\\[.*\\]");
		Matcher matcher = p.matcher(jsonContent);
		matcher.find();
		jsonContent = matcher.group();
		
		StockInformPO list = new StockInformPO(id);
		
		//分别提取每一个大括号内的内容
		Pattern aDayPattern = Pattern.compile("\\{.+?\\}");
		matcher = aDayPattern.matcher(jsonContent);
		Pattern concreteDataPattern = Pattern.compile("\\\"\\w+\\\": ?.+?[,}]");
		while(matcher.find()){
			String dayData = matcher.group();
			Map<String, String> dataMap = new HashMap<String, String>();
			
			Matcher im = concreteDataPattern.matcher(dayData);
			while(im.find()){
				String part = im.group();
				String[] d = part.split("\": ");
				String key = d[0].substring(1);
				String object = d[1].substring(0, d[1].length() - 1);
				if(object.startsWith("\"")){
					object = object.substring(1, object.length() - 1);
				}
				dataMap.put(key, object);
			}
			
			//屏蔽成交量是0的StockDataPO，将其不加入列表
			if(Double.valueOf(dataMap.get("volume")) == 0){
				continue;
			}
			
			StockOneDayPO day = new StockOneDayPO(id,dataMap);
			list.addOneDay(day);
		}
		
		return list;
	}
}
