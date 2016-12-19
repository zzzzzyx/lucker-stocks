package cn.edu.nju.luckers.calculate_center.data.network.service;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.vo.IndustryFlowVO;

public interface IndustryFlowDataGetter {
         
	public ArrayList<IndustryFlowVO> getIndustryFlow() throws IOException, ConnectionFailure;
	
}
