package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.cache;

import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class CacheDataGetter {

	ThreadRecorder recorder;
	ArrayList<String> idList;
	
	public CacheDataGetter(ArrayList<String> idList) {
		this.idList = idList;
		recorder = new ThreadRecorder();
		for(int i = 0;i<idList.size();i++){
			recorder.addCache(new CacheRecord(idList.get(i)));
		}
	}
	
	public ArrayList<String> getList(){
		return idList;
	}
	
	public void start(){
		for(int i =0;i<idList.size();i++){
			Thread t =new Thread(new StockGetThread(recorder.get(idList.get(i))));
			t.start();
		}
	}
	
	public StockInformVO get(String id,String date){
		try{
			return recorder.get(id).get(date);
		}catch(NullPointerException e){
			return null;
		}
		
	}
}
