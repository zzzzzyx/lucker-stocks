package cn.edu.nju.luckers.luckers_stocks.vo;

import java.util.Map;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.DetailServiceImpl.StockCalculator;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.data.network.po.stock.StockDataPO;

/**
 * @description 股票概要信息的VO对象 
 * 其保存的内容是，某一只股票某一天所有的数据项
 * @author Mzc
 * @date 2016/3/4
 */
public class StockInformVO {
   
	private String ID;//股票代号
    private String name;//股票名字
    private Map<String, String> datas;//由PO层传来的数据，此处建议修改
    
    public StockInformVO(String ID,String name,StockDataPO dataPO){
    	//构造器
    	datas=dataPO.getDataMap();
    	this.ID=ID;
    	this.name=name;
    	StockCalculator cal = new StockCalculator(ID, datas.get(DataKey.date.toString()));
    	datas.put("rf",cal.calculateRf( ));
    	datas.put("average_5",cal.calculateAverage(5));
    	datas.put("average_10",cal.calculateAverage(10));
    	datas.put("average_20",cal.calculateAverage(20));
    	datas.put("average_60",cal.calculateAverage(60));
    }
    
    
    public void setName(String name){
    	this.name = name;
    }
    
    /**
     * @description 新的get方法，可以直接用关键字获得所需数据
     * 可以使用的关键字参考DataKey
     * @param key
     * @return
     */
    public String getItem(DataKey key){
    	return (String) datas.get(key.toString());
    }
    
    //单独的get某个数据项
    public String getID() {
 		return ID;
 	}
 	public String getName() {
 		return name;
 	}
    /**
     * 以下方法已经废弃请不要再调用，调用上面的方法即可
     */
 
	public String getOpen() {
		return (String) datas.get("open");
	}
	public String getHigh() {
		return (String) datas.get("high");
	}
	public String getLow() {
		return (String) datas.get("low");
	}
	public String getClose() {
		return (String) datas.get("close");
	}
	public String getAdjprice() {
		return (String) datas.get("adj_price");
	}
	public String getVolume() {
		return (String) datas.get("volume");
	}
	public String getTurnover() {
		return (String) datas.get("turnover");
	}
	public String getPe() {
		return (String) datas.get("pe_ttm");
	}
	public String getPb() {
		return (String) datas.get("pb");
	}
	public String getDate(){
		return (String) datas.get("date");
	}
    
}
