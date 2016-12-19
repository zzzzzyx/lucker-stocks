package cn.edu.nju.luckers.luckers_stocks.data.network.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;

public class StockDataProvider implements StockDataGetter{

	ArrayList<String> stockIDList;
	Map<String, String> stock_urlMap;
	RawStringHandler handler;
	public StockDataProvider() {
		handler = new RawStringHandler();
	}

	@Override
	public ArrayList<String> getStockIDList(String year, String exchange) throws IOException, ConnectionFailure{
		String bonus = UrlBonusMaker.makeStockListUrlBonus(year, exchange);
		String jsonContent  = JavaUrlConnecter.getURLContent(JavaUrlConnecter.anyquant_url
				+ JavaUrlConnecter.stockList + bonus,"gbk");
		ArrayList<String> list = handler.getStockNameList(jsonContent);
		return list;
	}

	@Override
	public StockPO getStockByID(String id, String startDay, String endDay, String fields) throws IOException, ConnectionFailure {
		String bonus = UrlBonusMaker.makeStockUrlBonus(id, startDay, endDay, fields);
		String jsonContent  = JavaUrlConnecter.getURLContent(JavaUrlConnecter.anyquant_url
				+ JavaUrlConnecter.stock + bonus,"gbk");
		StockPO stock = handler.handleStock(jsonContent,id);
		return stock;
	}

	@Override
	public StockPO getBenchmarkByID(String id, String startDay, String endDay, String fields) throws IOException, ConnectionFailure {
	
		String bonus = UrlBonusMaker.makeStockUrlBonus(id, startDay, endDay, fields);
		String jsonContent  = JavaUrlConnecter.getURLContent(JavaUrlConnecter.anyquant_url
				+ JavaUrlConnecter.benchmark + bonus,"gbk");
		StockPO benchmark = handler.handleStock(jsonContent, id);
		return benchmark;
	}
	
	@Override
	public ArrayList<String> getStockName(ArrayList<String> stockCodeList) throws IOException, ConnectionFailure {
		
		String url = "http://hq.sinajs.cn/list=";
		ArrayList<String> result = new ArrayList<>(stockCodeList.size());
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

	@Override
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
}
