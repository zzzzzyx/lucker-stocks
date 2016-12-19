package cn.edu.nju.luckers.luckers_stocks.data.network;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.*;

import cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem.StockBufferReader;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.impl.StockDataProvider;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockDataPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;

public class StockDataGetterTest {
	
	StockDataGetter s;
	public static final Pattern stockCodePattern = Pattern.compile("[a-zA-Z]{2}[0-9]{6}");
	public static final Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
	public static final Pattern digitPattern = Pattern.compile("[0-9]+");
	public static final Pattern decimalPattern = Pattern.compile("[0-9]+\\.[0-9]+");
	
	@Before
	public void initial(){
		s = new StockBufferReader();
	}
	
	@Test
	public void testBenchmarkByID() throws IOException, ConnectionFailure{
		StockPO benchmark = s.getBenchmarkByID(StockDataGetter.BENCHMARK_HS300, null, null, null);
		assertStock(benchmark,StockDataGetter.BENCHMARK_HS300);
	}
	
	@Test
	public void testStockByID() throws IOException, ConnectionFailure{
		StockPO stock1 = s.getStockByID("sh600978", "2016-01-15", "2016-05-24", null);
		assertStock(stock1,"sh600978");
		StockPO stock2 = s.getStockByID("sz000410", null , null, null);
		assertStock(stock2,"sz000410");
	}
	
	private void assertStock(StockPO stock,String stockid){
		assertEquals(stockid,stock.getId());
		ArrayList<StockDataPO> dayList = stock.getDayList();
		assertTrue(dayList.size() >= 1);
		for(StockDataPO data : dayList){
			String date = data.getDate();
			Matcher matcher = datePattern.matcher(date);
			assertTrue(matcher.find());
			
			String volumn = data.getDataMap().get("volume");
			matcher = digitPattern.matcher(volumn);
			assertTrue(matcher.find());
		}
	}
	
	@Ignore
	@Test
	public void testStockList() throws IOException, ConnectionFailure{
		
		ArrayList<String> list1 = s.getStockIDList("2012", StockDataProvider.SZ);
		assertStockListTrue(list1);
		ArrayList<String> list2 = s.getStockIDList(null, StockDataProvider.SH);
		assertStockListTrue(list2);
	}
	
	private void assertStockListTrue(ArrayList<String> list){
		for(int i = 0 ; i < 30 ; i ++){
			String test = list.get(i);
			Matcher matcher = stockCodePattern.matcher(test);
			assertTrue(matcher.find());
		}
	}
	
	@Test
	public void testStockName() throws IOException, ConnectionFailure{
		ArrayList<String> codeList = new ArrayList<>();
		codeList.add("sh600666");
		codeList.add("sz000410");
		codeList.add("sz002565");
		codeList.add("sh600499");
		
		ArrayList<String> nameList = new ArrayList<>();
		nameList.add("奥瑞德");
		nameList.add("沈阳机床");
		nameList.add("上海绿新");
		nameList.add("科达洁能");
		
		assertEquals(nameList, s.getStockName(codeList));
	}

	@Ignore
	@Test
	public void testIndustryName() throws IOException, ConnectionFailure{
		assertEquals(s.getIndustryName("sh600756"),"信息技术");
		assertEquals(s.getIndustryName("sz000736"),"房地产业");
		assertEquals(s.getIndustryName("sh601579"),"茶酒饮料");
		assertEquals(s.getIndustryName("sz300249"),"专用设备制造");
	}
}
