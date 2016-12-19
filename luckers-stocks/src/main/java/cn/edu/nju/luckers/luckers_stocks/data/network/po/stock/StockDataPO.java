package cn.edu.nju.luckers.luckers_stocks.data.network.po.stock;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Zyx
 * 这是每一日的某只股票数据，包含于StockPO中
 */
public class StockDataPO {

	/**
	 * map的key如下：均为String
	 * date,open,high,low,close,adj_price,volume,turnover,pe_ttm,pb;
	 * 其中date关键字对应的日期与date变量的值相同
	 */
	private Map<String,String> dataMap;

	/**
	 * 日期，格式为：2005-01-19
	 */
	private String date;
	
	/**
	 * 这是可以读取的 <股票> 域的类型
	 * open: 开盘价
	 * high: 最高价
	 * low: 最低价
	 * close: 收盘价
	 * adj_price: 后复权价
	 * volume: 成交量
	 * turnover: 换手率
	 * pe: 市盈率；绩优股；成长股；
	 * pb: 市净率
	 */
	public static final ArrayList<String> STOCK_FIELDS_TYPE = new ArrayList<String>();
	static{
		STOCK_FIELDS_TYPE.add("date");
		STOCK_FIELDS_TYPE.add("open");
		STOCK_FIELDS_TYPE.add("high");
		STOCK_FIELDS_TYPE.add("low");
		STOCK_FIELDS_TYPE.add("close");
		STOCK_FIELDS_TYPE.add("adj_price");
		STOCK_FIELDS_TYPE.add("volume");
		STOCK_FIELDS_TYPE.add("pe_ttm");
		STOCK_FIELDS_TYPE.add("pb");
		STOCK_FIELDS_TYPE.add("turnover");
	}
	
	/**
	 * 这是可以读取的 <大盘指数> 域的类型
	 * open: 开盘价
	 * high: 最高价
	 * low: 最低价
	 * close: 收盘价
	 * adj_price: 后复权价
	 * volume: 成交量
	 */
	public static final ArrayList<String> BENCHMARK_FIELDS_TYPE = new ArrayList<String>();
	static{
		BENCHMARK_FIELDS_TYPE.add("volume");
		BENCHMARK_FIELDS_TYPE.add("high");
		BENCHMARK_FIELDS_TYPE.add("adj_price");
		BENCHMARK_FIELDS_TYPE.add("low");
		BENCHMARK_FIELDS_TYPE.add("date");
		BENCHMARK_FIELDS_TYPE.add("open");
		BENCHMARK_FIELDS_TYPE.add("close");
	}
	
	
	
	public StockDataPO(Map<String,String> dataMap) {
		super();
		this.dataMap = dataMap;
		date = dataMap.get("date");
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(String s : STOCK_FIELDS_TYPE){
			str.append( '<' + s + " :" + dataMap.get(s) + "> ");
		}
		return str.toString();
	}
	
	public String getDate() {
		return date;
	}
	
	public Map<String, String> getDataMap() {
		return dataMap;
	}
	
}
