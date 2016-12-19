package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.cache;

import java.util.HashMap;

import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

/**
 * 该类是一支股票在内存中的保存记录
 * @author NjuMzc
 *
 */
public class CacheRecord {

	String id;//这只股票的id
	HashMap<String,StockInformVO> map ;
	
	public CacheRecord(String id) {
		this.id = id;
		this.map = new HashMap<>();
	}
	
	public void add(StockInformVO vo){
		map.put(vo.getDate(), vo);
	}
	
    public StockInformVO get(String date){
    	return map.get(date);
    }
	
}
