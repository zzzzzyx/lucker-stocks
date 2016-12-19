package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl.DetailSeriveImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem.StockBufferReader;
import cn.edu.nju.luckers.luckers_stocks.data.network.impl.StockDataProvider;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class DetailSeriveTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        StockDetailService service =LogicController.getInstance().getDetailServer();
//        StockDetailVO vo = service.getDetails("sz300020");
        
//        System.out.println(vo.getOpenList().size());
        
//        Iterator<StockInformVO> voList = service.getDetails("sz000959", MyCalendar.getDayByNum(14), MyCalendar.getToday());
//       while(voList.hasNext()){
//    	   StockInformVO vo = voList.next();
//    	   System.out.println("日期："+vo.getDate()+"最高价："+vo.getItem(DataKey.high));
//       }
        
        Iterator<StockInformVO> vos = service.getDetails("sz002445", "2016-02-22", "2016-02-26");
        
        System.out.println(vos == null);
	}

}
