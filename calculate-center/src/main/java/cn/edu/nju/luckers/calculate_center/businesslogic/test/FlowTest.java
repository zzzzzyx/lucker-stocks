package cn.edu.nju.luckers.calculate_center.businesslogic.test;

import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.IndustryFlowService;
import cn.edu.nju.luckers.calculate_center.vo.IndustryFlowVO;

public class FlowTest {

	public static void main(String[] arg){
		IndustryFlowService service=LogicController.getFlowService();
		 ArrayList<IndustryFlowVO> voList=service.getIndustryFlow();
		 for(int i=0;i<voList.size();i++){
			 IndustryFlowVO thisVo=voList.get(i);
			 System.out.println("name="+thisVo.getName()+"  flow="+thisVo.getFlow()+" 第"+(i+1)+"个行业");
		 }
	}
}
