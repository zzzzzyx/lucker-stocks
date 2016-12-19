package cn.edu.nju.luckers.luckers_stocks.data.network.po.stock;

import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.data.ZyxCalender;

/**
 * @author Zyx
 * 这是某只股票的在某段时间的数据
 */
public class StockPO {

	/**
	 * 股票编号
	 */
	private String id;
	
	/**
	 * 如果没有内容，列表为空，不为null
	 */
	private ArrayList<StockDataPO> dayList;
	
	
	public StockPO(String id, ArrayList<StockDataPO> dayList) {
		super();
		this.id = id;
		this.dayList = dayList;
	}
	public StockPO(StockPO origin, String beginDay, String endDay){
		this.id = origin.id;
		this.dayList = new ArrayList<>();
		for(StockDataPO data : origin.dayList){
			if(ZyxCalender.leftIsSmallerOrEqual(beginDay, data.getDate()) && 
					ZyxCalender.leftIsSmallerOrEqual(data.getDate(), endDay)){
				this.dayList.add(data);
			}
		}
	}
	public String getId() {
		return id;
	}
	public ArrayList<StockDataPO> getDayList() {
		return dayList;
	}
}
