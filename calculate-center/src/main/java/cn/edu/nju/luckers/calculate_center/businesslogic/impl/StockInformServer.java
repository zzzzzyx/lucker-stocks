package cn.edu.nju.luckers.calculate_center.businesslogic.impl;

import java.io.IOException;
import java.util.ArrayList;


import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import cn.edu.nju.luckers.calculate_center.data.DataController;
import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.calculate_center.global.DataKey;
import cn.edu.nju.luckers.calculate_center.global.MyCalendar;
import cn.edu.nju.luckers.calculate_center.po.StockInformPO;
import cn.edu.nju.luckers.calculate_center.po.StockOneDayPO;
import cn.edu.nju.luckers.calculate_center.vo.StockImVO;
import cn.edu.nju.luckers.calculate_center.vo.StockInformVO;

public class StockInformServer implements StockInformService {
	
	StockDataGetter dataGetter;
	
	public StockInformServer(){
		dataGetter = DataController.getDataGetter();
	}

	/**
	 * 获取股票名称方法的实现
	 */

	public String getName(String id) {
		ArrayList<String> ids = new ArrayList<String>();
		ids.add(id);
		ArrayList<String> list = new ArrayList<String>();
		try {
			list = dataGetter.getStockName(ids);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			return null;
		}
		
		if(list.isEmpty()||list.get(0).equals("\"")||list.get(0).equals("")
				||list.get(0).equals("FAILED\"")){
			return null;
		}else{
			return list.get(0);
		}
	}

	/**
	 * 获取股票信息的实现
	 * 可指定时间段获得一段时间的信息
	 * Attention!!!!!!!!!!!!!!!
	 * 获得的数据包括开始日期，但不包括结束日期
	 * 如果某日期数据不存在，则该日期不会加在链表中
	 * 按日期从前到后顺序存在链表中
	 * 
	 */
	public ArrayList<StockInformVO> getInform(String id, String start, String end) {
		ArrayList<StockOneDayPO> pos = new ArrayList<StockOneDayPO>();
		StockInformPO po = null;
		try {
			po = dataGetter.getStockByID(id, start, end);
		} catch (IOException e) {	
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			return null;
		}
		
		pos = po.getInform();
		
		ArrayList<StockInformVO> vos = new ArrayList<StockInformVO>();
		
		for(int i=0;i<pos.size();i++){
			StockInformVO vo = new StockInformVO(id, pos.get(i));
			
			//计算涨跌幅的部分
			if(i!=0){
				StockInformVO pre = vos.get(i-1);
				double close1 = Double.valueOf(pre.getItem(DataKey.close));
				double close2 = Double.valueOf(vo.getItem(DataKey.close));

				double rf = (close2 - close1)/close1;
			    vo.setRf(rf);
			}else{
				StockOneDayPO yes = null;
				String temp = MyCalendar.getYesterday(start);
				while(yes == null){
					try {
						yes = dataGetter.getStockByID(id, temp);
					} catch (IOException e) {
						
						e.printStackTrace();
					} catch (ConnectionFailure e) {
						
						e.printStackTrace();
					}
					temp = MyCalendar.getYesterday(temp);
				}
				
				double close1 = Double.valueOf(yes.getDataMap().get("close"));
				double close2 = Double.valueOf(vo.getItem(DataKey.close));
				double rf = (close2 - close1)/close1;
				vo.setRf(rf);
			}
			
			vos.add(vo);
		}
		
		return vos;
	}

	/**
	 * 获取单日股票信息的实现
	 */

	public StockInformVO getInform(String id, String date) {
		StockOneDayPO po = null;
		try {
			po = dataGetter.getStockByID(id, date);
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			
			return null;
		}
		if(po == null){
			return null;
		}
		StockInformVO vo = new StockInformVO(id, po);
		
		//计算涨跌幅
		StockOneDayPO yes = null;
		String temp = MyCalendar.getYesterday(date);
		while(yes == null){
			try {
				yes = dataGetter.getStockByID(id, temp);
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ConnectionFailure e) {
				
				e.printStackTrace();
			}
			temp = MyCalendar.getYesterday(temp);
		}
		
		double close1 = Double.valueOf(yes.getDataMap().get("close"));
		double close2 = Double.valueOf(vo.getItem(DataKey.close));
		double rf = (close2 - close1)/close1;
		vo.setRf(rf);
		return vo;
	}

	public String getIndustry(String id) {
		String industry = "";
		
		try {
			industry = dataGetter.getIndustryName(id);
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			
			return null;
		}
		
		return industry;
	}

	public ArrayList<StockImVO> getImmediate(String id) {
		ArrayList<StockImVO> vos = new ArrayList<StockImVO>();
		
		ArrayList<StockInformVO> last = getInform(id, MyCalendar.getDayByNum(7), MyCalendar.getToday());
		
		
		
		try {
			vos = dataGetter.getImmediate(id);
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			return null;
		}
		
		if(last.size() == 0){
		    return vos;
		}
		StockInformVO vo = last.get(last.size()- 1);
		double close = Double.valueOf(vo.getItem(DataKey.close));
		if(close == 0){
			return vos;
		}
		for(int i =0;i<vos.size();i++){
			double r = (Double.valueOf(vos.get(i).getPrice()) - close)/close;
			
			
			vos.get(i).setRf(String.valueOf(r));
		}
		
		return vos;
	}

}
