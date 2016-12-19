package cn.edu.nju.luckers.calculate_center.businesslogic.impl;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.businesslogic.service.KGraphService;
import cn.edu.nju.luckers.calculate_center.data.DataController;
import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.service.KGraphDataGetter;
import cn.edu.nju.luckers.calculate_center.po.KGraphPO;

public class KGraphServer implements KGraphService {
	
	private KGraphDataGetter dataGetter;
	
	public KGraphServer(){
		dataGetter = DataController.getKDataGetter();
	}

	public ArrayList<KGraphPO> getDayGraph(String id) {
		ArrayList<KGraphPO> list = new ArrayList<KGraphPO>();
		try {
			list = dataGetter.getDayGraph(id);
		} catch (IOException e) {
			
		} catch (ConnectionFailure e) {
			
			return null;
		}
		return list;
	}

	public ArrayList<KGraphPO> getWeekGraph(String id) {
		ArrayList<KGraphPO> list = new ArrayList<KGraphPO>();
		try {
			list = dataGetter.getWeekGraph(id);
		} catch (IOException e) {
			
		} catch (ConnectionFailure e) {
			return null;
		}
		return list;
	}

	public ArrayList<KGraphPO> getMonGraph(String id) {
		ArrayList<KGraphPO> list = new ArrayList<KGraphPO>();
		try {
			list = dataGetter.getMonGraph(id);
		} catch (IOException e) {
			
		} catch (ConnectionFailure e) {
			
			return null;
		}
		return list;
	}

}
