package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.cache.CacheDataGetter;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.ExceptionReporter;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.NameList;
import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.impl.StringStorageManager;
import cn.edu.nju.luckers.luckers_stocks.data.localFileSystem.service.StringStorageService;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockDataPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

/**
 * @description 查看股票详细信息功能的接口实现
 * @author Mzc
 * @date 2016/3/7
 *
 */
public class DetailSeriveImpl implements StockDetailService {
	StockDataGetter dataGetter;// 网络服务接口
	CacheDataGetter cacheServer;// 内存缓存服务接口

	public DetailSeriveImpl(StockDataGetter dataGetter) {
		// TODO Auto-generated constructor stub

		this.dataGetter = dataGetter;
		try {
			this.cacheServer = new CacheDataGetter(
					new StringStorageManager(StringStorageService.FAVORITES_FILE_PATH).getContent());
			cacheServer.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Iterator<StockInformVO> getDetails(String Id, String start, String end) {
        //日期错误，股票不存在会返回null
		if(start.equals(end)){
			return null;
		}
		// 先获取股票的名字
		String name = NameList.getInstance().getName(Id);
		if(name == null){
			return null;
		}
		ArrayList<StockInformVO> voList = new ArrayList<>();
		String tempDate = start;
		// 还要判断会不会是因为start日期本身不是交易日而造成每次都获取失败

		if (cacheServer.get(Id, start) != null) {
			// 说明Cache中已经包含了所有需要的数据
	    	while (!tempDate.equals(end)) {
				StockInformVO vo = cacheServer.get(Id, tempDate);
				if (vo != null) {
					vo.setName(name);
					voList.add(vo);
				}
				tempDate = MyCalendar.getTomorrow(tempDate);
			}
			return voList.iterator();
		} 
		else {
			StockPO po = null;
			try {
				po = dataGetter.getStockByID(Id, start, MyCalendar.getTomorrow(start), null);
				ArrayList<StockDataPO> datas = po.getDayList();
				if (datas.size() == 0) {
					String newDate = MyCalendar.getTomorrow(start);
					return getDetails(Id, newDate, end);
				}
			} catch (IOException | ConnectionFailure e) {
				ExceptionReporter exr = ExceptionReporter.getInstance();
				exr.setException("Internet Connect Failure");
				
			}
			
		}

		try {
			StockPO po = dataGetter.getStockByID(Id, start, end, null);
			ArrayList<StockDataPO> datas = po.getDayList();
			for (int i = 0; i < datas.size(); i++) {

				StockInformVO vo = new StockInformVO(Id, name, datas.get(i));
				voList.add(vo);
			}

		} catch (IOException e) {
			e.printStackTrace();
	
			return null;
		} catch (ConnectionFailure e) {
			// 断线异常处理
			ExceptionReporter exr = ExceptionReporter.getInstance();
			exr.setException("Internet Connect Failure");
			
		}
		
		if(voList.size()==0){

			return null;
		}
		
		return voList.iterator();
	}

	@Override
	public Iterator<StockInformVO> getDetails(String Id) {
		return getDetails(Id, MyCalendar.getDayByNum(30), MyCalendar.getToday());
	}

	@Override
	public StockInformVO getOneDay(String id, String date) {
		
		Iterator<StockInformVO> voList = getDetails(id,date,MyCalendar.getTomorrow(date));

		if(voList == null){
			return null;
		}
		
		StockInformVO vo = null;
		if(voList.hasNext()){
			vo = voList.next();
		}else {
			return null;
		}
		return vo;
		
	}

}
