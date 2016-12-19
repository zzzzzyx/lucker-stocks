package cn.edu.nju.luckers.luckers_stocks.vo;

import java.util.Map;

/**
 * 一张K线图的VO对象
 * @author NjuMzc
 *
 */
public class KGraphVO {

	private String id;
	
	private String date;//如果是周K，则是周五的日期；如果是月K，则是该月的月底日
	
	private double high;//最高价,如果是周K，则是一周的最大值，如果是月K，同理
	
	private double low;//最低价
	
	private double volume;//成交量，周K则是一周的成交量之和，月K同理
	
	private double open;//开盘价
	
	private double close;//收盘价
	
	private double average_5;//5日/周/月均值
	
	private double average_10;//10
	
	private double average_20;//20
	
	

	public  KGraphVO(String id,String date,Map<String, Double> datas){
		this.id=id;
	    this.date=date;
	    
	    high = datas.get("high");
	    low = datas.get("low");
	    volume = datas.get("volume");
	    open = datas.get("open");
	    close = datas.get("close");
	    average_5=datas.get("average_5");
	    average_10 = datas.get("average_10");
	    average_20 = datas.get("average_20");
		
	}
	
	
	//Getters
	public String getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public double getHigh() {
		return high;
	}

	public double getLow() {
		return low;
	}

	public double getVolume() {
		return volume;
	}

	public double getOpen() {
		return open;
	}

	public double getClose() {
		return close;
	}

	public double getAverage_5() {
		return average_5;
	}

	public double getAverage_10() {
		return average_10;
	}

	public double getAverage_20() {
		return average_20;
	}
    	
}
