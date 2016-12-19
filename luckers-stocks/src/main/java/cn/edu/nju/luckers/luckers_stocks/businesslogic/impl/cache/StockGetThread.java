package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.cache;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.ExceptionReporter;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.impl.StockDataProvider;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockDataPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class StockGetThread implements Runnable{
	
	String id;//一个线程负责一支股票
	String date;//储存日期的变量
	StockDataGetter dataGetter; //使用已写好的逻辑接口
	CacheRecord record;
	
	public StockGetThread(CacheRecord record) {
		this.date = MyCalendar.getToday();
		this.id = record.id;
		this.record = record;
		this.dataGetter = new StockDataProvider();
	}

	/**
	 * 当线程开始，从今天开始向前不断的读取数据到内存之中
	 * 一次读取100天
	 */
	@Override
	public void run() {
		while(true){
			try {
				StockPO po = dataGetter.getStockByID(id, MyCalendar.getDayByNum(date, 50), date, null);
				ArrayList<StockDataPO> datas = po.getDayList();
				for(int i=0;i<datas.size();i++){
					StockInformVO vo = new StockInformVO(id, null, datas.get(i));
					record.add(vo);
				}
				
					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			} catch (ConnectionFailure e) {
				// 断线异常处理
				ExceptionReporter exr = ExceptionReporter.getInstance();
				exr.setException("Internet Connect Failure");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
				
			} catch(NullPointerException e){
				//偶尔还会出现Null指针的问题
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			
			date = MyCalendar.getDayByNum(date, 50);
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			
			if(date.substring(0, 4).equals("2010")){
				break;
			}
		}
		
		
	}

}
