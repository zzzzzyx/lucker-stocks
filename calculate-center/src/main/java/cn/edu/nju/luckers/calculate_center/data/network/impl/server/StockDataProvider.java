package cn.edu.nju.luckers.calculate_center.data.network.impl.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.impl.networkTools.JavaUrlConnecter;
import cn.edu.nju.luckers.calculate_center.data.network.impl.networkTools.RawStringHandler;
import cn.edu.nju.luckers.calculate_center.data.network.impl.networkTools.UrlBonusMaker;
import cn.edu.nju.luckers.calculate_center.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.calculate_center.global.MyCalendar;
import cn.edu.nju.luckers.calculate_center.po.StockInformPO;
import cn.edu.nju.luckers.calculate_center.po.StockOneDayPO;
import cn.edu.nju.luckers.calculate_center.vo.StockImVO;

public class StockDataProvider implements StockDataGetter{

	ArrayList<String> stockIDList;
	Map<String, String> stock_urlMap;
	RawStringHandler handler;
	public StockDataProvider() {
		handler = new RawStringHandler();
	}


	public ArrayList<String> getStockName(ArrayList<String> stockCodeList) throws IOException, ConnectionFailure {
		
		String url = "http://hq.sinajs.cn/list=";
		ArrayList<String> result = new ArrayList<String>(stockCodeList.size());
		if(stockCodeList.size() < 100){
			for(String s : stockCodeList){
				url += s + ',';
			}
			String r = JavaUrlConnecter.getURLContent(url,"gbk");
			String[] splitent = r.split(";");
			for(String s : splitent){
				result.add(s.split("=\"")[1].split(",")[0]);
			}
		}
		else{
			final int segment = 3;
			
			for(int seg = 0 ; seg < segment ; seg++){
				for(int i = stockCodeList.size()*seg/segment  ; i < stockCodeList.size()*(seg+1)/segment ; i++){
					url += stockCodeList.get(i) + ',';
				}
				String r = JavaUrlConnecter.getURLContent(url,"gbk");
				String[] splitent = r.split(";");
				for(String s : splitent){
					result.add(s.split("=\"")[1].split(",")[0]);
				}			
			}
		}
		
		return result;
	}

	
	public String getIndustryName(String stockCode) throws IOException, ConnectionFailure {
		
		String industryName = JavaUrlConnecter.getURLContent
				(JavaUrlConnecter.stockIndustryURL_head + 
						stockCode.substring(2,stockCode.length()) + 
						JavaUrlConnecter.stockIndustryURL_tail
						,"utf-8");
		
		industryName = industryName.split("<div class=\"inner_box industry_info\"> ")[1];
		industryName = industryName.split("target=\"_blank\">")[1];
		industryName = industryName.split("</a>")[0];
		
		return industryName;
	}

	public StockOneDayPO getStockByID(String id, String date) throws IOException, ConnectionFailure {
		StockInformPO list = getStockByID(id, date, MyCalendar.getTomorrow(date));
		
		if(list.getInform().size() == 0)
			return null;
		else
			return list.getInform().get(0);
	}

	public StockInformPO getStockByID(String id, String start, String end) throws IOException, ConnectionFailure {
		String bonus = UrlBonusMaker.makeStockUrlBonus(id, start, end, null);
		String jsonContent  = JavaUrlConnecter.getURLContent(JavaUrlConnecter.anyquant_url
				+ JavaUrlConnecter.stock + bonus,"gbk");
		StockInformPO stock = handler.handleStock(jsonContent,id);
		return stock;
	}


	public ArrayList<StockImVO> getImmediate(String id) throws IOException, ConnectionFailure {
		
		String url = "http://img1.money.126.net/data/hs/time/today/";
		String mark = "";
		
		if(id.substring(0, 2).equals("sz")){
			mark = "1";
		}else{
			mark = "0";
		}
		url = url + mark + id.substring(2,8) + ".json";
		String webinfo = JavaUrlConnecter.getURLContent(url, "gbk");
		
		ArrayList<StockImVO> list = handleInfo(webinfo);
		
		return list;
	}
	
	private ArrayList<StockImVO> handleInfo(String info){
		ArrayList<StockImVO> list = new ArrayList<StockImVO>();
		
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
				String time = infos[0].substring(1, 5);
				String price = infos[1];
				String average = infos[2];
				String volume = infos[3];
				
				StockImVO vo = new StockImVO(time, price, average, volume);
				list.add(vo);
			}else{
				continue;
			}
		}
						
		return list;
	}
}
