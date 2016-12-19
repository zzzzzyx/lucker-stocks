package cn.edu.nju.luckers.luckers_stocks.businesslogic.detailService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.*;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.StockInformVOVerifier;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl.DataScreenServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl.DetailSeriveImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.DataScreenService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem.StockBufferReader;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import cn.edu.nju.luckers.luckers_stocks.vo.StockScreenVO;

public class DetailServiceTest {

	DataScreenService screen;
	StockDetailService stockDetail;
	StockDataGetter dataGetter;
	
	@Before
	public void initial(){
		dataGetter = new StockBufferReader();
		screen = new DataScreenServiceImpl(stockDetail);
		stockDetail = new DetailSeriveImpl(dataGetter);
	}
	
	@Ignore
	@Test
	public void testDetailScreenService(){
		
		StockScreenVO sv1=new StockScreenVO(DataKey.close, "24", "50");
		StockScreenVO sv2=new StockScreenVO(DataKey.high, "20", "40");
		ArrayList<StockScreenVO> list = new ArrayList<>();
		list.add(sv1);
		list.add(sv2);
		Iterator<StockInformVO> it = screen.getChosen("sh600978", "2015-01-15", "2015-02-24",list);
		
		while(it.hasNext()){
		    StockInformVO vo = it.next();
		    StockInformVOVerifier.testStockInformVO(vo);
		    double close = Double.parseDouble(vo.getItem(DataKey.close));
		    double high = Double.parseDouble(vo.getItem(DataKey.high));
		    assertTrue(close > 24);
		    assertTrue(close < 50);
		    assertTrue(high > 20);
		    assertTrue(high < 40);
		}
		
		sv1=new StockScreenVO(DataKey.turnover, "2", "5");
		sv2=new StockScreenVO(DataKey.low, "15", "40");
		list = new ArrayList<>();
		list.add(sv1);
		list.add(sv2);
		it = screen.getChosen("sh600978", "2015-02-03","2015-04-06",list);
		
		while(it.hasNext()){
		    StockInformVO vo = it.next();
		    StockInformVOVerifier.testStockInformVO(vo);
		    double turnover = Double.parseDouble(vo.getItem(DataKey.turnover));
		    double low = Double.parseDouble(vo.getItem(DataKey.low));
		    assertTrue(turnover > 2);
		    assertTrue(turnover < 5);
		    assertTrue(low > 15);
		    assertTrue(low < 40);
		}
		
		sv1=new StockScreenVO(DataKey.volume, "20000", "30000");
		list = new ArrayList<>();
		list.add(sv1);
		it = screen.getChosen("sz000410","2013-03-07","2015-04-06",list);
		
		while(it.hasNext()){
		    StockInformVO vo = it.next();
		    StockInformVOVerifier.testStockInformVO(vo);
		    double volume = Double.parseDouble(vo.getItem(DataKey.volume));
		    assertTrue(volume > 20000);
		    assertTrue(volume < 30000);
		}
	}
	
	@Test
	public void testGetDetails(){
		System.out.println("get one day");
		assertNotNull(stockDetail.getOneDay("sz002084", "2016-04-06").getName());
		System.out.println("get one day");
		assertNotNull(stockDetail.getOneDay("sz002084", "2016-04-06").getName());
		System.out.println("get one day");
		assertNotNull(stockDetail.getOneDay("sz002084", "2016-04-06").getName());
//		StockInformVOVerifier.testStockInformVO(stockDetail.getOneDay("sz002084", "2016-04-06"));
//		assertNotNull(stockDetail.getOneDay("sz002084", "2015-03-05").getName());
//		StockInformVOVerifier.testStockInformVO(stockDetail.getOneDay("sz002084", "2015-03-05"));
//		assertNull(stockDetail.getOneDay("sz002084", "2015-03-12"));
//		assertNull(stockDetail.getOneDay("sz002084", "2016-04-09"));
		
//		assertGetDetails("sz002084");
//		assertGetDetails("sz000410");
//		assertNull(stockDetail.getDetails("sz002084"));
		
//		assertGetDetails("sz002084", "2014-03-07","2015-04-06");
//		assertGetDetails("sz002084", "2015-04-03","2015-04-06");
//		assertNull(stockDetail.getDetails("ssz002084", "2015-01-15", "2015-02-24"));
	}

	private void assertGetDetails(String id){
		Iterator<StockInformVO> it = stockDetail.getDetails(id);
		while(it.hasNext()){
			StockInformVOVerifier.testStockInformVO(it.next());
		}
	}
	private void assertGetDetails(String id, String start, String end){
		Iterator<StockInformVO> it = stockDetail.getDetails(id, start, end);
		while(it.hasNext()){
			StockInformVOVerifier.testStockInformVO(it.next());
		}
	}
}
