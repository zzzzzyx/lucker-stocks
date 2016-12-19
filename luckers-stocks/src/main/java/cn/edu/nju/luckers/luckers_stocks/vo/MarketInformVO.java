package cn.edu.nju.luckers.luckers_stocks.vo;

import java.util.Map;

import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockDataPO;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockPO;
/**
 * @description 大盘概略信息，保存的是最近一天的大盘数据
 * @author Mzc
 * @date 2016/3/8
 */
public class MarketInformVO {

	String id;//大盘名
	Map<String,String> dataMap;//数据表
	String date;//大盘日期
	
	public MarketInformVO(StockPO po){
	       this.id=po.getId();
	       StockDataPO data = po.getDayList().get(0);
	       this.dataMap = data.getDataMap();
	}
	
	
	//Getters
	public String getName(){
		return id;
	}
	
	public String getDate(){
		return dataMap.get("date");
	}
	
	public String getVolume(){
		return dataMap.get("volume");
	}
	
	public String getHigh(){
		return dataMap.get("high");
	}
	
	public String getAdj(){
		return dataMap.get("adj_price");
	}
	
	public String getLow(){
		return dataMap.get("low");
	}
	
	public String getClose(){
		return dataMap.get("close");
	}
	
	public String getOpen(){
		return dataMap.get("open");
	}
}
