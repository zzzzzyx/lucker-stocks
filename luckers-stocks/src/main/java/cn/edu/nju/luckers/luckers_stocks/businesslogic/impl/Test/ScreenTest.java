package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.DataScreenService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import cn.edu.nju.luckers.luckers_stocks.vo.StockScreenVO;

public class ScreenTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
          
		DataScreenService screenServer = LogicController.getInstance().getDataScreenServer();
		
		StockScreenVO sv1=new StockScreenVO(DataKey.close, "0", "50");
		StockScreenVO sv2=new StockScreenVO(DataKey.high, "20", "50");
		ArrayList<StockScreenVO> list = new ArrayList<>();
		list.add(sv1);
		list.add(sv2);
		Iterator<StockInformVO> it = screenServer.getChosen("sz300020", "2016-01-01","2016-03-03",list);
		
		while(it.hasNext()){
		    StockInformVO vo = it.next();
		    System.out.println(vo.getDate()+" close "+vo.getItem(DataKey.close)+"high "+vo.getItem(DataKey.high));
		}
	}

}
