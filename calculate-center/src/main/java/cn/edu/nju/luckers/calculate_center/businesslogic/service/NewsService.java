package cn.edu.nju.luckers.calculate_center.businesslogic.service;

import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.po.NewsPO;

public interface NewsService {
	
	/**
	 * 获得指定股票的最近的10条新闻
	 * 链表内按照时间由最近到远排列
	 * @param id
	 * @return
	 */
	public ArrayList<NewsPO> getNews(String id);
	
}
