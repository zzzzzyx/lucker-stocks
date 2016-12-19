package cn.edu.nju.luckers.calculate_center.data.network.service;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;

public interface MarketDataGetter {
	/**
	 * 参数列表：
	 * 下标              含义
	 *  0              主力流入
	 *  1		主力流出
	 *  2		净流量
	 *  3		超大流入
	 *  4		超大流出
	 *  5		大单流入
	 *  6		大单流出
	 *  7		中单流入
	 *  8		中单流出
	 *  9		小单流入
	 *  10		小单流出
	 *  11		最新价
	 *  12		开盘价
	 *  13		最高价
	 *  14		最低价
	 *  15		涨跌幅
	 *  16		涨跌额
	 *  17		总量
	 *  18		总额
	 *  19		换手率
	 *  20		流值
	 *  21		内盘
	 *  22		外盘
	 *  23		振幅
	 *  24		涨家数
	 *  25		跌家数
	 *  26		平家数
	 * @param id 只接受"sz","sh"
	 * @return 如果有任何错误，则return null
	 * @throws ConnectionFailure 
	 * @throws IOException 
	 */
	public ArrayList<String> getMarket(String id) throws IOException, ConnectionFailure;

}
