package cn.edu.nju.luckers.luckers_stocks.businesslogic.favouriteService;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.*;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.StockInformVOVerifier;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl.DetailSeriveImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.FavouriteServiceImpl.FavouriteServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.FavouriteService.FavouriteService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem.StockBufferReader;
import cn.edu.nju.luckers.luckers_stocks.data.network.StockDataGetterTest;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class FavoriteServiceTest {

	FavouriteService service;
	StockDataGetter dataGetter;
	
	@Before
	public void initial(){
		dataGetter = new StockBufferReader();
		StockDetailService detailService = new DetailSeriveImpl(dataGetter);
		service = new FavouriteServiceImpl(detailService);
	}
	
	@Test
	public void testGetContent(){
		Iterator<StockInformVO> initIterator = service.GetContent();
		while(initIterator.hasNext()){
			StockInformVOVerifier.testStockInformVO(initIterator.next());
		}
		
	}
	
	@Test
	public void testAddAndDeleteAndGet(){
		
		// clone the initial arraylist
		ArrayList<String> initialList = new ArrayList<>();
		ArrayList<String> stockCodeList = new ArrayList<>();
		for(String s : service.getList()){
			stockCodeList.add(new String(s));
			initialList.add(new String(s));
		}
		
		service.add("sh600666");
		stockCodeList.add("sh600666");
		assertEquals(stockCodeList, service.getList());
		
//		service.add("sz000410");
//		stockCodeList.add("sz000410");
//		assertEquals(stockCodeList, service.getList());
		
		service.add("sz002565");
		stockCodeList.add("sz002565");
		assertEquals(stockCodeList, service.getList());
		
		service.remove("sh600666");
		stockCodeList.remove("sh600666");
		assertEquals(stockCodeList, service.getList());
		
		service.add("sh600499");
		stockCodeList.add("sh600499");
		assertEquals(stockCodeList, service.getList());
		
		service.remove("sz002565");
		stockCodeList.remove("sz002565");
		assertEquals(stockCodeList, service.getList());
		
		service.remove("sh600499");
		stockCodeList.remove("sh600499");
		assertEquals(stockCodeList, service.getList());
		
//		service.remove("sz000410");
//		stockCodeList.remove("sz000410");
//		assertEquals(stockCodeList, service.getList());
		assertEquals(initialList, service.getList());
	}
}
