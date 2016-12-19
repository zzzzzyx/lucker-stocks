package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.IndustryServiceImpl.IndustryInformServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.ScoreCalServiceImpl.IndustryScoreCalculator;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.vo.IndustryVO;

public class IndustryTest {

	public static void main(String[] args) {
		IndustryInformServiceImpl impl = new IndustryInformServiceImpl(LogicController.getInstance().getFavouriteServer(),
				LogicController.getInstance().getDataGetter(),LogicController.getInstance().getDetailServer());
		
//		Iterator<IndustryVO> map = impl.getIndustryList();
//		while(map.hasNext()){
//			IndustryVO vo =map.next();
//			System.out.println(vo.getName());
//			IndustryScoreCalculator cal = new IndustryScoreCalculator(LogicController.getInstance().getDetailServer());
//			
//			
//			cal.calculate(vo, "2016-03-21", MyCalendar.getToday());
//		}
		
		impl.getIndustryScore("采矿业","2016-03-21", MyCalendar.getToday());
		
		
	

	}

}
