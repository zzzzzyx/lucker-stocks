package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.MarketServiceImpl.MarketDetailServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.MarketServiceImpl.MarketInformServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService.MarketInformService;
import cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem.StockBufferReader;
import cn.edu.nju.luckers.luckers_stocks.data.network.impl.StockDataProvider;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockDataPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.MarketDetailVO;
import cn.edu.nju.luckers.luckers_stocks.vo.MarketInformVO;

public class MarketServiceTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//        MarketInformService service= new MarketInformServiceImpl(new StockDataProvider());
//		MarketInformVO vo = service.getItems("hs300");
//        
//        System.out.println(vo.getDate()+"  "+vo.getHigh());
		
		MarketDetailServiceImpl service = new MarketDetailServiceImpl(new StockBufferReader() );
		MarketDetailVO vo = service.getDetails("hs300", null, null);

		ArrayList<String> dateList=vo.getDateList();
		ArrayList<String> openList = vo.getOpenList();
		
		for(int i=0;i<dateList.size();i++){
			System.out.println(dateList.get(i)+" "+openList.get(i));
		}
		
	}

}
