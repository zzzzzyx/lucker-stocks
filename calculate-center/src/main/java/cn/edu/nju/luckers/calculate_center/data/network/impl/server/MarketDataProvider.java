package cn.edu.nju.luckers.calculate_center.data.network.impl.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.impl.networkTools.JavaUrlConnecter;
import cn.edu.nju.luckers.calculate_center.data.network.service.MarketDataGetter;

public class MarketDataProvider implements MarketDataGetter {

	public ArrayList<String> getMarket(String id) throws IOException, ConnectionFailure {
		String marketId ="";
		if(id.equals("sz")){
			marketId = "zs000001";
		}else{
			marketId = "zs399001";
		}
		
		String url = "http://quote.eastmoney.com/"+marketId+".html";
		
		String webinfo = JavaUrlConnecter.getURLContent(url, "utf-8");
		
		String temp = webinfo.split("cnt_zjl")[1];
		
		String div1 = temp.split("txR")[1];
		
		return null;
	}

	private ArrayList<String> getNum(String info){
		ArrayList<String> ll = new ArrayList<String>();
	    
	    Pattern p = Pattern.compile("(\\d+)");
		Matcher matcher = p.matcher(info);
		
		while(matcher.find()){
			String temp1 = matcher.group();
			ll.add(temp1);
		}
	    
	    for(int i=0;i<ll.size();i++){
	    	System.out.println(ll.get(i));
	    }
		
		return ll;	
	}
}
