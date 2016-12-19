package cn.edu.nju.luckers.luckers_stocks.data.network.service;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;

public interface StockDataGetter {

	public static final String SZ = "sz";
	public static final String SH = "sh";
	public static final String BENCHMARK_HS300 = "hs300";
	/**
	 * @param year 年份，可以为null，将返回默认结果（我也不知道默认结果是啥，下同）
	 * @param exchange 交易所，只有三个可选值:StockDataGetter.SZ（代表深圳交易所）
	 *  ，StockDataGetter.SH（代表上海交易所） 和 null，如果输入其他值
	 *  ，将返回未知结果或抛出未知异常，输入null将返回默认结果
	 * @return 返回在指定条件下的股票代码名单
	 * @throws IOException 代表从服务器上读取此列表失败
	 * @throws ConnectionFailure 
	 */
	public ArrayList<String> getStockIDList(String year, String exchange) throws IOException, ConnectionFailure;
	
	/**
	 * @param id 比如：sh600000  不能为null
	 * @param startDay 起始时间，格式'YYYY-mm-dd'，可以为null（起始时间和结束时间不同时为null时，我也不知道会返回什么）
	 * @param endDay 结束时间，格式'YYYY-mm-dd'，可以为null（起始时间和结束时间不同时为null时，我也不知道会返回什么）
	 * @param fields 指定数据字段（可以为null，为null时返回全部交易数据）
	 * 格式： name1+name2+name3（可以有多个），具体字段请参考为StockDataPO.STOCK_FIELDS_TYPE
	 * ：open: 开盘价，high: 最高价
	 * ，low: 最低价，close: 收盘价，adj_price: 后复权价，volume: 成交量，turnover: 换手率
	 * ，pe: 市盈率，pb: 市净率
	 * @return 返回指定股票代码的股票交易数据，默认返回过去一个月的全部交易数据
     * @throws IOException 代表从服务器上读取此股票失败	 
     * PS by MZC:该方法最快只能获得昨天的数据，且返回时不包括结束日期的数据，想要某一天的数据
	 *           起始日期选择当天，结束日期选择后一天
	 *           获得不了当天的数据真实脑残....	 
	 * @throws ConnectionFailure */
	public StockPO getStockByID(String id,String startDay, String endDay,String fields) throws IOException, ConnectionFailure;
	
	/**
	 * @param id 比如：hs300，也可以调用此接口的静态常量  不能为null
	 * @param startDay 起始时间，格式'YYYY-mm-dd'，可以为null（起始时间和结束时间不同时为null时，我也不知道会返回什么）
	 * @param endDay 结束时间，格式'YYYY-mm-dd'，可以为null（起始时间和结束时间不同时为null时，我也不知道会返回什么）
	 * @param fields 指定数据字段（可以为null，为null时返回全部交易数据）
	 * 格式： name1+name2+name3（可以有多个），具体字段请参考StockDataPO.BENCHMARK_FIELDS_TYPE
	 * @return 返回指定大盘指数代码的交易数据，默认返回一年内的所有数据（经过测试得出的结论）
     * @throws IOException 代表从服务器上读取此股票失败	 
	 * @throws ConnectionFailure 
     */
	public StockPO getBenchmarkByID(String id,String startDay, String endDay,String fields) throws IOException, ConnectionFailure;

	/**
	 * 根据输入的股票去读取其对应的名字
	 * Copyright@ 新浪财经，如侵必删，详请联系@author zzzzzyx@outlook.com
	 * @param stockCodeList 
	 * @return 返回的结果列表与输入的列表按顺序一一对应，如果列表某项股票无法读取，对应的一项值为null
	 * @throws IOException
	 * @throws ConnectionFailure 
	 */
	public ArrayList<String> getStockName(ArrayList<String> stockCodeList) throws IOException, ConnectionFailure;
	
	/**
	 * 根据股票代码从外网读取其行业名，如输入为'sh600485'（即信威集团）, 将返回'通信设备'
	 * Copyright@ 网易财经，如侵必删，详请联系@author zzzzzyx@outlook.com
	 * @param stockCode 股票代码
	 * @return 行业名称
	 * @throws IOException
	 * @throws ConnectionFailure 
	 */
	public String getIndustryName(String stockCode) throws IOException, ConnectionFailure;
}
