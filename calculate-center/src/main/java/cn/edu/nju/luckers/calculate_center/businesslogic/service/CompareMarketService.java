package cn.edu.nju.luckers.calculate_center.businesslogic.service;

import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.vo.MarketCompareVO;

public interface CompareMarketService {

	/**
	 * 对比个股和大盘的方法
	 * @param id 如果代号错误，返回null
	 * @return vo中包含日期，大盘价和个股价，用相应的get方法即可获得
	 */
	public ArrayList<MarketCompareVO> compareMarket(String id);
	
}
