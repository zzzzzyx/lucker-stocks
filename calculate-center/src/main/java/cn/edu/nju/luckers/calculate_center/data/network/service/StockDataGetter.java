package cn.edu.nju.luckers.calculate_center.data.network.service;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.po.StockInformPO;
import cn.edu.nju.luckers.calculate_center.po.StockOneDayPO;
import cn.edu.nju.luckers.calculate_center.vo.StockImVO;

public interface StockDataGetter {

	public static final String SZ = "sz";
	public static final String SH = "sh";
	public static final String BENCHMARK_HS300 = "hs300";
	
	/**
	 * @param id 比如：sh600000  不能为null
	 * @param date 指定的日期,格式为yyyy-mm-dd
	 * 格式： name1+name2+name3（可以有多个），具体字段请参考为StockDataPO.STOCK_FIELDS_TYPE
	 * ：open: 开盘价，high: 最高价
	 * ，low: 最低价，close: 收盘价，adj_price: 后复权价，volume: 成交量，turnover: 换手率
	 * ，pe: 市盈率，pb: 市净率
	 * @return 返回指定股票指定日期的数据；
	 *         如果当天的数据不存在，则返回null 
	 * @throws ConnectionFailure 
	 * @attention 每次调用该方法都是一次网络访问，请不要用循环语句调用该方法否则会雪崩
	 */
	public StockOneDayPO getStockByID(String id,String date) throws IOException, ConnectionFailure;
	
	/**
	 * 上一个方法的加强版本，只进行一次网络访问
	 * @param id
	 * @param start
	 * @param end
	 * @return
	 * @throws IOException
	 * @throws ConnectionFailure
	 */
	public StockInformPO getStockByID(String id,String start,String end) throws IOException, ConnectionFailure;
	

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
	
	/**
	 * 获得当天实时股票数据的方法
	 * @param id
	 * @return
	 * @throws ConnectionFailure 
	 * @throws IOException 
	 */
	public ArrayList<StockImVO> getImmediate(String id) throws IOException, ConnectionFailure ;
}
