package cn.edu.nju.luckers.calculate_center.data.network.impl.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.impl.networkTools.JavaUrlConnecter;
import cn.edu.nju.luckers.calculate_center.data.network.service.KGraphDataGetter;
import cn.edu.nju.luckers.calculate_center.po.KGraphPO;

public class KGraphDataProvider implements KGraphDataGetter {
	

	public ArrayList<KGraphPO> getDayGraph(String id) throws IOException, ConnectionFailure {
		String url = "http://img1.money.126.net/data/hs/kline/day/history/2016/";
		String mark = "";
		
		if(id.substring(0, 2).equals("sz")){
			mark = "1";
		}else{
			mark = "0";
		}
		url = url + mark + id.substring(2,8) + ".json";
		String webinfo = JavaUrlConnecter.getURLContent(url, "gbk");
		
		ArrayList<KGraphPO> list = handleInfo(webinfo);
		
		return list;
	}

	public ArrayList<KGraphPO> getWeekGraph(String id)throws IOException, ConnectionFailure {
		String url1 = "http://img1.money.126.net/data/hs/kline/week/history/2014/";
		String url2 = "http://img1.money.126.net/data/hs/kline/week/history/2015/";
		String url3 = "http://img1.money.126.net/data/hs/kline/week/history/2016/";
		String mark = "";
		
		if(id.substring(0, 2).equals("sz")){
			mark = "1";
		}else{
			mark = "0";
		}
		url1 = url1 + mark + id.substring(2,8) + ".json";
		url2 = url2 + mark + id.substring(2,8) + ".json";
		url3 = url3 + mark + id.substring(2,8) + ".json";
		String webinfo1 = JavaUrlConnecter.getURLContent(url1, "gbk");
		String webinfo2 = JavaUrlConnecter.getURLContent(url2, "gbk");
		String webinfo3 = JavaUrlConnecter.getURLContent(url3, "gbk");
		
		ArrayList<KGraphPO> list1 = handleInfo(webinfo1);
		ArrayList<KGraphPO> list2 = handleInfo(webinfo2);
		ArrayList<KGraphPO> list3 = handleInfo(webinfo3);
		
		list1 = appendList(list1, list2);
		list1 = appendList(list1, list3);
			
		return list1;
	}

	public ArrayList<KGraphPO> getMonGraph(String id)throws IOException, ConnectionFailure {
		String year = "";
		int y = 2010;
		
		String mark = "";
		
		if(id.substring(0, 2).equals("sz")){
			mark = "1";
		}else{
			mark = "0";
		}
		
		ArrayList<KGraphPO> list = new ArrayList<KGraphPO>();
		
		for(;y<=2016;y++){
			year = String.valueOf(y);
			String url = "http://img1.money.126.net/data/hs/kline/month/history/";
			url = url + year + "/" + mark + id.substring(2,8) + ".json";
			
			String webinfo = "";
			try{
				 webinfo = JavaUrlConnecter.getURLContent(url, "gbk");
			}catch(ConnectionFailure e){
				 continue;
			}
			
			list = appendList(list, handleInfo(webinfo));
		}
		
		
		return list;
	}
	
	
	private ArrayList<KGraphPO> handleInfo(String info){
		ArrayList<KGraphPO> list = new ArrayList<KGraphPO>();
		
        ArrayList<String> ll = new ArrayList<String>();
		
		Pattern p = Pattern.compile("\\[.*\\]");
		Matcher matcher = p.matcher(info);
		
		while(matcher.find()){
			String temp = matcher.group();
			ll.add(temp);
		}
		String[] temp = ll.get(0).split("\\[");
		for(int i=0;i<temp.length;i++){
			String now = temp[i];
			if(!now.equals("")){
				String infos[] = now.split(",");
				String date = infos[0].substring(1, 9);
				String open = infos[1];
				String close = infos[2];
				String high = infos[3];
				String low = infos[4];
				String volume = infos[5];
				
				KGraphPO po =new KGraphPO(date, open, close, high, low, volume);
				list.add(po);
			}else{
				continue;
			}
		}
						
		return list;
	}
 
	private ArrayList<KGraphPO> appendList(ArrayList<KGraphPO> list1,ArrayList<KGraphPO> list2){
		for(int i=0;i<list2.size();i++){
			list1.add(list2.get(i));
		}
		return list1;
	}
}
