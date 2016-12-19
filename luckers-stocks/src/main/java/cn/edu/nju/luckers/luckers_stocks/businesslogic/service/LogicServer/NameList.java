package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.edu.nju.luckers.luckers_stocks.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.luckers_stocks.data.network.service.StockDataGetter;

public class NameList {

	private StockDataGetter dataGetter;
	
	private static NameList Singlation = null;
	
	private Map<String, String>  list;
	
	private NameList(StockDataGetter dataGetter){
		this.dataGetter=dataGetter;
		list = new HashMap<>();
	}
	
    public static NameList getInstance(){
    	if(Singlation == null){
    		Singlation = new NameList(LogicController.getInstance().getDataGetter());
    		
    	}
    	return Singlation;
    }
	
    public String getName(String id){
    	if(list.get(id)!=null){
    		return list.get(id);
    	}else{
    		String name = "";
    		ArrayList<String> ids = new ArrayList<>();
    		ids.add(id);

    		ArrayList<String> namel = new ArrayList<>();
    		try {
    			namel = dataGetter.getStockName(ids);
    		} catch (IOException e) {
    			
    			e.printStackTrace();
    		} catch (ConnectionFailure e){
    			ExceptionReporter exListener= ExceptionReporter.getInstance();
    			exListener.setException("Internet Connect Failure");
    			return null;
    		}
    		if(namel == null){
    			return null;
    		}
    		if (namel.size() == 0) {
    			name = "";
    		} else {
    			name = namel.get(0);
    		}
    		
    		list.put(id, name);
    		return name;
    	}
    }
}
