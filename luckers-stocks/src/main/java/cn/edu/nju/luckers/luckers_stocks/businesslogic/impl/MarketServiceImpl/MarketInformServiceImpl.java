package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.MarketServiceImpl;

import java.io.IOException;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.ExceptionReporter;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService.MarketInformService;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.MarketInformVO;

public class MarketInformServiceImpl implements MarketInformService {

	StockDataGetter dataGetter;

	public MarketInformServiceImpl(StockDataGetter dataGetter) {
		// TODO Auto-generated constructor stub
		this.dataGetter = dataGetter;

	}

	@Override
	public MarketInformVO getItems(String id) {
		MarketInformVO vo=null;
		StockPO po = null;
		
		// 要测试最新能获得的大盘数据
		int testNum = 1;
		while (true) {
			try {
				po = dataGetter.getBenchmarkByID(id, MyCalendar.getDayByNum(testNum), MyCalendar.getToday(), null);
				if (po.getDayList().size() == 0) {
					testNum++;
				} else {
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ConnectionFailure e) {
				// 断线异常处理
				ExceptionReporter exListener= ExceptionReporter.getInstance();
				exListener.setException("Internet Connect Failure");
				e.printStackTrace();
			} 
		}
		
		vo= new MarketInformVO(po);
		return vo;
	}

}
