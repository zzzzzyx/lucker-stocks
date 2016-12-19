package cn.edu.nju.luckers.luckers_stocks.data.network.impl;

public class UrlBonusMaker {

	public static String makeStockListUrlBonus(String year, String exchange) {
		if(year == null && exchange == null){
			return "";
		}
		else{
			StringBuilder str = new StringBuilder("?");
			if(year !=null){
				str.append("year=" + year);
				if(exchange !=null) str.append('&');
			}
			if(exchange !=null){
				str.append("exchange=" + exchange);
			}
			return str.toString();
		}
	}

	public static String makeStockUrlBonus(String id, String startDay, String endDay, String fields) {
		StringBuilder str = new StringBuilder(id + '/');
		if(startDay == null && endDay == null && fields == null){}
		else{
			str.append("?");
			if(startDay !=null){
				str.append("start=" + startDay + '&');
			}
			if(endDay !=null){
				str.append("end=" + endDay + '&');
			}
			if(fields !=null){
				str.append("fields=" + fields + '&');
			}
			str.deleteCharAt(str.length() - 1);
		}
		
		
		return str.toString();
	}
}
