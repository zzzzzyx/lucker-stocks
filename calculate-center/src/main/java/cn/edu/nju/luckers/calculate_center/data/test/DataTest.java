package cn.edu.nju.luckers.calculate_center.data.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.corba.se.spi.orb.DataCollector;

import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import cn.edu.nju.luckers.calculate_center.data.DataController;
import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.impl.server.StockDataProvider;
import cn.edu.nju.luckers.calculate_center.data.network.service.MarketDataGetter;
import cn.edu.nju.luckers.calculate_center.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.calculate_center.po.KGraphPO;
import cn.edu.nju.luckers.calculate_center.po.NewsPO;
import cn.edu.nju.luckers.calculate_center.po.StockInformPO;
import cn.edu.nju.luckers.calculate_center.po.StockOneDayPO;
import cn.edu.nju.luckers.calculate_center.vo.IndustryFlowVO;
import cn.edu.nju.luckers.calculate_center.vo.StockImVO;

public class DataTest {

	public static void main(String[] args) throws IOException, ConnectionFailure {
//		StockDataProvider server = new StockDataProvider();
		
		//StockOneDayPO po = server.getStockByID("sz002445","2016-05-02");
//		StockInformPO pos = server.getStockByID("sz002445", "2016-05-02", "2016-05-07");
//
//		ArrayList<StockOneDayPO> list = pos.getInform();
//		Iterator<StockOneDayPO> it = list.iterator();
//		while(it.hasNext()){
//			StockOneDayPO po = it.next();
//			System.out.println(po.getDate()+" "+po.getDataMap().get("high"));
//		}
		
//		ArrayList<KGraphPO> list = DataController.getKDataGetter().getMonGraph("sz000001");
//		for(int i=0;i<list.size();i++){
//			System.out.println(list.get(i).getDate()+" "+list.get(i).getVolume());
//		}
//		StockDataGetter server = DataController.getDataGetter();
//		server.getStockByID("sz000001", "2016-01-08","2016-02-08");
//		ArrayList list = new ArrayList<String>();
//		list.add("sz000001");
//		server.getStockName(list);
//		server.getImmediate("sz000001");
//		
//		for(int i=0;i<list.size();i++){
//			System.out.println(list.get(i).getTime()+" "+list.get(i).getPrice()+list.get(i).getAverage());
//		}
		
//		for(int i=0;i<list.size();i++){
//			System.out.println();
//		}
		
		MarketDataGetter server = DataController.getMarketDataGetter();
		
		server.getMarket("sz");
	}

}
