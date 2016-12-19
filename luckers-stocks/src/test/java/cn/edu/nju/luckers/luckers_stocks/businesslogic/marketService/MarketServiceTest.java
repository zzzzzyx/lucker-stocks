package cn.edu.nju.luckers.luckers_stocks.businesslogic.marketService;


import static org.junit.Assert.*;

import java.util.regex.Matcher;

import org.junit.*;

import cn.edu.nju.luckers.luckers_stocks.PatternHolder;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.MarketServiceImpl.MarketDetailServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.MarketServiceImpl.MarketInformServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService.MarketDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService.MarketInformService;
import cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem.StockBufferReader;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.MarketDetailVO;

/**
 * @author S3
 * 暂时不测试MarketInformService，原因：并没有用上这个接口 TODO
 */
public class MarketServiceTest {

	MarketDetailService detailService;
	MarketInformService informService;
	StockDataGetter dataGetter;
	
	@Before
	public void initialize(){
		dataGetter = new StockBufferReader();
		detailService = new MarketDetailServiceImpl(dataGetter);
		informService = new MarketInformServiceImpl(dataGetter);
	}
	
	@Test
	public void testDetail(){
		MarketDetailVO vo = detailService.getDetails("hs300", "2013-11-15", "2015-02-09");
		assertTrue(vo.getAdjList().size() > 250);
		for(String s : vo.getDateList()){
			Matcher matcher = PatternHolder.datePattern.matcher(s);
			assertTrue(matcher.find());
		}
		for(String s : vo.getAdjList()){
			Matcher matcher = PatternHolder.decimalPattern.matcher(s);
			assertTrue(matcher.find());
		}
		for(String s : vo.getOpenList()){
			Matcher matcher = PatternHolder.decimalPattern.matcher(s);
			assertTrue(matcher.find());
		}
		for(String s : vo.getHighList()){
			Matcher matcher = PatternHolder.decimalPattern.matcher(s);
			assertTrue(matcher.find());
		}
		for(String s : vo.getVolumeList()){
			Matcher matcher = PatternHolder.digitPattern.matcher(s);
			assertTrue(matcher.find());
		}
		
		vo = detailService.getDetails("hs300", "2016-04-02", "2016-04-11");
		assertTrue(vo.getAdjList().size() > 3);
		assertTrue(vo.getAdjList().size() < 8);
		for(String s : vo.getDateList()){
			Matcher matcher = PatternHolder.datePattern.matcher(s);
			assertTrue(matcher.find());
		}
		for(String s : vo.getAdjList()){
			Matcher matcher = PatternHolder.decimalPattern.matcher(s);
			assertTrue(matcher.find());
		}
		for(String s : vo.getOpenList()){
			Matcher matcher = PatternHolder.decimalPattern.matcher(s);
			assertTrue(matcher.find());
		}
		for(String s : vo.getHighList()){
			Matcher matcher = PatternHolder.decimalPattern.matcher(s);
			assertTrue(matcher.find());
		}
		for(String s : vo.getVolumeList()){
			Matcher matcher = PatternHolder.digitPattern.matcher(s);
			assertTrue(matcher.find());
		}
	}
}
