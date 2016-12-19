package cn.edu.nju.luckers.calculate_center.businesslogic.service;

import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.po.KGraphPO;

public interface KGraphService {
	
	//该方法也可以获得大盘的K线图数据
	//sh000001可以获得上证指数
	//sz399001可以获得深圳成指

	/**
	 * 获得日K图的方法
	 * 会返回2016年的所有日K数据
	 * @param id
	 * @return
	 */
	public ArrayList<KGraphPO> getDayGraph(String id);
	
	/**
	 * 获得周K图的方法
	 * 会返回2014年到2016年这三年内的周K数据
	 * @param id
	 * @return
	 */
	public ArrayList<KGraphPO> getWeekGraph(String id);
	
	/**
	 * 获得月K图的方法
	 * 会返回2010到2016的月K数据
	 * @param id
	 * @return
	 */
	public ArrayList<KGraphPO> getMonGraph(String id);
}
