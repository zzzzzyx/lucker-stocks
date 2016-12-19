package cn.edu.nju.luckers.calculate_center.businesslogic.impl;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.businesslogic.service.IndustryFlowService;
import cn.edu.nju.luckers.calculate_center.data.DataController;
import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.vo.IndustryFlowVO;

public class IndusrtyFlowServer implements IndustryFlowService {

	public ArrayList<IndustryFlowVO> getIndustryFlow() {
		ArrayList<IndustryFlowVO> list = new ArrayList<IndustryFlowVO>();
		try {
			list = DataController.getFlowDataGetter().getIndustryFlow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			return null;
		}
		return list;
	}

}
