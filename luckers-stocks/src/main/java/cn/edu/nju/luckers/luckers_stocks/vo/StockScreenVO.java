package cn.edu.nju.luckers.luckers_stocks.vo;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;

/**
 * @description 用于传递筛选信息的vo对象
 * @author Mzc
 * @date 2016/3/12
 */
public class StockScreenVO {
	DataKey key;// 关键字
	String min;// 最小值
	String max;// 最大值

	public StockScreenVO(DataKey key, String min, String max) {
		this.key = key;
		this.min = min;
		this.max = max;
	}

	// Getters
	public DataKey getKey() {
		return key;
	}

	public String getMin() {
		return min;
	}

	public String getMax() {
		return max;
	}
}
