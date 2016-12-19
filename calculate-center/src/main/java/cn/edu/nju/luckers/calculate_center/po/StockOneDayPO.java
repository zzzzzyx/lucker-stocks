package cn.edu.nju.luckers.calculate_center.po;

import java.util.ArrayList;
import java.util.Map;

public class StockOneDayPO {

	String id;
	
	/**
	 * map的key如下：均为String
	 * date,open,high,low,close,adj_price,volume,turnover,pe_ttm,pb;
	 * 其中date关键字对应的日期与date变量的值相同
	 */
	private Map<String,String> dataMap;

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
	 * rf: 涨跌幅
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
	
	
	public StockOneDayPO(String id,Map<String,String> dataMap) {
		super();
		this.dataMap = dataMap;
		this.id = id;
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
		return dataMap.get("date");
	}
	
	public String getId(){
		return id;
	}
	
	public Map<String, String> getDataMap() {
		return dataMap;
	}
	
}
