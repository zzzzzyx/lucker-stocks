package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.MarketServiceImpl;

import java.io.IOException;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.ExceptionReporter;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService.MarketDetailService;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.MarketDetailVO;

public class MarketDetailServiceImpl implements MarketDetailService {
	
	StockDataGetter dataGetter;
	
	public MarketDetailServiceImpl(StockDataGetter dataGetter) {
		this.dataGetter = dataGetter;
	}

	@Override
	public MarketDetailVO getDetails(String id, String start, String end) {
		// TODO Auto-generated method stub
		StockPO po = null;
		try {
			po = dataGetter.getBenchmarkByID(id, start, end, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			// 断线异常处理
			ExceptionReporter exListener= ExceptionReporter.getInstance();
			exListener.setException("Internet Connect Failure");
			e.printStackTrace();
			return null;
		}
		
		MarketDetailVO vo = new MarketDetailVO(po);
		
		return vo;
	}


}
