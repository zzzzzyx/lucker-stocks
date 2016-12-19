package cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.data.ZyxCalender;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.impl.StockDataProvider;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;

public class StockBufferReader implements StockDataGetter{

	private StockDataGetter stockDataProvider;
	private static final String stockIndustryStoragePath = "local/stockIndustryStorage.sb";
	static final String specialSpliter = " @:@ ";
	public StockBufferReader() {
		stockDataProvider = new StockDataProvider();
	}
	@Override
	public ArrayList<String> getStockIDList(String year, String exchange) throws IOException, ConnectionFailure {
		return stockDataProvider.getStockIDList(year, exchange);
	}

	@Override
	public StockPO getStockByID(String id, String startDay, String endDay, String fields) throws IOException, ConnectionFailure {		
		StockPO stockBigger = stockDataProvider.getStockByID(id, startDay, endDay, fields);
		return stockBigger;
	}

	@Override
	public StockPO getBenchmarkByID(String id, String startDay, String endDay, String fields) throws IOException, ConnectionFailure {
		return stockDataProvider.getBenchmarkByID(id, startDay, endDay, fields);
	}

	@Override
	public ArrayList<String> getStockName(ArrayList<String> stockCodeList) throws IOException, ConnectionFailure {
		return stockDataProvider.getStockName(stockCodeList);
	}

	@Override
	public String getIndustryName(String stockCode) throws IOException, ConnectionFailure {
		String result = new LocalBufferHandler() {
			
			@Override
			protected String readRemoteString() throws IOException, ConnectionFailure {
				return stockDataProvider.getIndustryName(stockCode);
			}
		}.rawRead(stockIndustryStoragePath, stockCode);
				
		return result;
	}

}
