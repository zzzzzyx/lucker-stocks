package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.KGraphServiceImpl.KGraphServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.KGraphService.KGraphService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.vo.KGraphVO;

public class KGraphTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        KGraphService service = new KGraphServiceImpl(LogicController.getInstance().getDetailServer());
        
        Iterator<KGraphVO> voList = service.getMonthK("sz002445", 6);
        
        while(voList.hasNext()){
        	KGraphVO vo = voList.next();
        	System.out.println(vo.getDate());
        	System.out.println(vo.getAverage_5());
        	System.out.println(vo.getVolume());
        	
        }
        
	}

}
