package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.cache;

import java.util.HashMap;

public class ThreadRecorder {

	HashMap<String, CacheRecord> records;
	
	public ThreadRecorder() {
		this.records = new HashMap<>();
	}
	
	public void addCache(CacheRecord cache){
		records.put(cache.id, cache);
		
	}
	
	public CacheRecord get(String id){
		return records.get(id);
	}
	
}
