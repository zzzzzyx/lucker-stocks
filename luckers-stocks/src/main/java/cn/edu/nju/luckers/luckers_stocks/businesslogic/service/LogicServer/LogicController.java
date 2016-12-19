package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer;

import java.util.Collection;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl.DataScreenServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl.DetailSeriveImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.FavouriteServiceImpl.FavouriteServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.IndustryServiceImpl.IndustryInformServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.KGraphServiceImpl.KGraphServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.ListServiceImpl.ListDelayServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.MarketServiceImpl.MarketDetailServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.MarketServiceImpl.MarketInformServiceImpl;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.DataScreenService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.FavouriteService.FavouriteService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.IndustryService.IndustryInformService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.KGraphService.KGraphService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ListService.ListDelayService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService.MarketDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService.MarketInformService;
import cn.edu.nju.luckers.luckers_stocks.data.localBufferSystem.StockBufferReader;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;

/**
 * @description 逻辑层接口实现的提供者，该类是一个单例
 * @author Mzc
 * @date 2016/3/8
 */
public class LogicController {
      
	private  static LogicController Singelation = null;
	private static StockDataGetter dataGetter = null;
	
	private  StockDetailService detailServer = null;
	private FavouriteService favouriteServer = null;
	private  ListDelayService listServer = null;
	private  MarketDetailService marketDetailServer = null;
	private  MarketInformService marketInformServer = null;
	private  DataScreenService dataScreenServer = null;
	private  IndustryInformService industryServer = null;
	private  KGraphService kGraphService = null;
	
	private LogicController(){
		dataGetter = new StockBufferReader();
		detailServer = new DetailSeriveImpl(dataGetter);
		favouriteServer = new FavouriteServiceImpl(detailServer);
		marketDetailServer = new MarketDetailServiceImpl(dataGetter);
		marketInformServer = new MarketInformServiceImpl(dataGetter);
		dataScreenServer = new DataScreenServiceImpl(detailServer);
		industryServer = new IndustryInformServiceImpl(favouriteServer, dataGetter,detailServer);
        kGraphService = new KGraphServiceImpl(detailServer);
	}
	
	
	public static LogicController getInstance(){
		if(Singelation==null){
			Singelation = new LogicController();
		}
		
		return Singelation;
	}
	
	public KGraphService getKGraphServer(){
		return kGraphService;
	}
	
	public IndustryInformService getIndustryServer(){
		return  industryServer;
	}
	
	
	public StockDataGetter getDataGetter(){
		return dataGetter;
	}
	public DataScreenService getDataScreenServer(){
		return dataScreenServer;
	}
	
	public  StockDetailService getDetailServer() {
		return detailServer;
	}

	public  FavouriteService getFavouriteServer() {
		return favouriteServer;
	}

	public  ListDelayService getListServer(String exchanger) {
		listServer = new ListDelayServiceImpl(exchanger, dataGetter);
		return listServer;
	}

	public  MarketDetailService getMarketDetailServer() {
		return marketDetailServer;
	}

	public  MarketInformService getMarketInformServer() {
		return marketInformServer;
	}
	
	
	/**
	 * 获得一个数据结构中最大值和最小值的方法
	 * @param list
	 * @return
	 */
	public static double getMin(Collection<Double> list){
		Iterator<Double> it =list.iterator();
		double min = Double.MAX_VALUE;
		while(it.hasNext()){
			Double num = it.next();
			if(num<min){
				min = num;
			}
		}
		return min;
	}
	public static double getMax(Collection<Double> list){
		Iterator<Double> it =list.iterator();
		double max = Double.MIN_VALUE;
		while(it.hasNext()){
			Double num = it.next();
			if(num>max){
				max = num;
			}
		}
		return max;
	}
}
