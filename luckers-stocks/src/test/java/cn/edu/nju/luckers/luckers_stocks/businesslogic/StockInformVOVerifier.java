package cn.edu.nju.luckers.luckers_stocks.businesslogic;

import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.data.network.StockDataGetterTest;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class StockInformVOVerifier {

	public static void testStockInformVO(StockInformVO stock){
		
		Matcher matcher = StockDataGetterTest.stockCodePattern.matcher(stock.getID());
		assertTrue(matcher.find());
		
		assertTrue(stock.getName().length() > 2);
		assertTrue(stock.getName().length() < 8);
		
		matcher = StockDataGetterTest.digitPattern.matcher(stock.getItem(DataKey.volume));
		assertTrue(matcher.find());
		matcher = StockDataGetterTest.decimalPattern.matcher(stock.getItem(DataKey.close));
		assertTrue(matcher.find());
		matcher = StockDataGetterTest.decimalPattern.matcher(stock.getItem(DataKey.low));
		assertTrue(matcher.find());
		matcher = StockDataGetterTest.decimalPattern.matcher(stock.getItem(DataKey.turnover));
		assertTrue(matcher.find());
	}
}
