package cn.edu.nju.luckers.calculate_center.vo;

import java.util.Map;

import cn.edu.nju.luckers.calculate_center.global.DataKey;
import cn.edu.nju.luckers.calculate_center.po.StockOneDayPO;


public class StockInformVO {
	private String Id;//股票代号
    private Map<String, String> datas;
    
    public StockInformVO(String Id,StockOneDayPO dataPO){
    	//构造器
    	datas=dataPO.getDataMap();
    	this.Id=Id;
    	datas.put("rf", "0");
    }
    
    public String getId(){
    	return Id;
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
    
    //每次都要用这个方法设置一下涨跌幅
    public void setRf(double rf){
    	String RF = String.valueOf(rf);
    	datas.replace("rf", RF);
    }
}
