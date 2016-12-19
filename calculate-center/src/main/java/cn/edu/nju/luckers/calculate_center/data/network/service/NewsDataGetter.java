package cn.edu.nju.luckers.calculate_center.data.network.service;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.po.NewsPO;

public interface NewsDataGetter {

	/**
	 * 获得指定股票的最近的10条新闻
	 * 链表内按照时间由最近到远排列
	 * @param id
	 * @return
	 * @throws ConnectionFailure 
	 * @throws IOException 
	 */
	public ArrayList<NewsPO> getNews(String id) throws IOException, ConnectionFailure;

}
