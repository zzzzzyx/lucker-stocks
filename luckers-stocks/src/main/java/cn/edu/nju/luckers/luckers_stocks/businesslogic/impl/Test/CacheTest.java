package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.cache.CacheDataGetter;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.cache.CacheRecord;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.cache.StockGetThread;

public class CacheTest {

	public static void main(String args[]){
		ArrayList<String> idList = new ArrayList<>();
		idList.add("sz300020");
		idList.add("sz000959");
		CacheDataGetter dataGetter = new CacheDataGetter(idList);
		dataGetter.start();
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try{
				System.out.println(dataGetter.get("sz300020", "2016-03-08").getHigh());
			}catch(NullPointerException e){
				System.out.println("null");
			}
			
		}
		
		
	}
	
	
	
}
