package cn.edu.nju.luckers.calculate_center.data.network.service;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.po.KGraphPO;

public interface KGraphDataGetter {
	
	public ArrayList<KGraphPO> getDayGraph(String id) throws IOException, ConnectionFailure;
	
	public ArrayList<KGraphPO> getWeekGraph(String id) throws IOException, ConnectionFailure;
	
	public ArrayList<KGraphPO> getMonGraph(String id) throws IOException, ConnectionFailure;

}
