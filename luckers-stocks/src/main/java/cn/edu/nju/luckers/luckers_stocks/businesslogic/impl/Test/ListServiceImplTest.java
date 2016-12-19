package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ListService.ListDelayService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class ListServiceImplTest {


 public static void main(String args[]){
		ListDelayService service = LogicController.getInstance().getListServer(null);

		Iterator<StockInformVO> voList = service.getItems("sh", 30);
		
		while(voList.hasNext()){
			StockInformVO vo=voList.next();
			System.out.println(vo.getName()+"收盘价"+vo.getClose()+"后复权价"+vo.getAdjprice());
		}
 }
	
}
