package cn.edu.nju.luckers.calculate_center.data;

import cn.edu.nju.luckers.calculate_center.data.network.impl.server.IndustryFlowDataProvider;
import cn.edu.nju.luckers.calculate_center.data.network.impl.server.KGraphDataProvider;
import cn.edu.nju.luckers.calculate_center.data.network.impl.server.MarketDataProvider;
import cn.edu.nju.luckers.calculate_center.data.network.impl.server.NewsDataProvider;
import cn.edu.nju.luckers.calculate_center.data.network.impl.server.StockDataProvider;
import cn.edu.nju.luckers.calculate_center.data.network.service.IndustryFlowDataGetter;
import cn.edu.nju.luckers.calculate_center.data.network.service.KGraphDataGetter;
import cn.edu.nju.luckers.calculate_center.data.network.service.MarketDataGetter;
import cn.edu.nju.luckers.calculate_center.data.network.service.NewsDataGetter;
import cn.edu.nju.luckers.calculate_center.data.network.service.StockDataGetter;

public class DataController {
	
	private static StockDataGetter dataGetter = null;
	
	private static KGraphDataGetter kdataGetter = null;
	
	private static NewsDataGetter newsDataGetter = null;
	
	private static IndustryFlowDataGetter flowDataGetter = null;
	
	private static MarketDataGetter marketDataGetter = null;
	

	public static StockDataGetter getDataGetter(){
		if(dataGetter == null){
			dataGetter = new StockDataProvider();
		}
		return dataGetter;
	}
	
	public static KGraphDataGetter getKDataGetter(){
		if(kdataGetter == null){
			kdataGetter = new KGraphDataProvider();
		}
		return kdataGetter;
	}
	
	public static NewsDataGetter getNewDataGetter(){
		if(newsDataGetter == null){
			newsDataGetter = new NewsDataProvider();
		}
		return newsDataGetter;
	}
	
	public static IndustryFlowDataGetter getFlowDataGetter(){
		if(flowDataGetter == null){
			flowDataGetter = new IndustryFlowDataProvider();
		}
		return flowDataGetter;
	}
	
	public static MarketDataGetter getMarketDataGetter(){
		if(marketDataGetter == null){
			marketDataGetter = new MarketDataProvider();
		}
		
		return marketDataGetter;
	}
}
