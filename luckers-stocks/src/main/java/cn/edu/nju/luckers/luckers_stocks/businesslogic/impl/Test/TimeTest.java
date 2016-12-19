package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.Test;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.LogicController;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;

public class TimeTest {

	public static void main(String[] args) {
//	  
//		ArrayList<String> list = MyCalendar.getTradingDays(MyCalendar.getDayByNum(7), MyCalendar.getToday());
//	    System.out.println(list);
		
//		System.out.println(MyCalendar.getDayByNum("2016-03-25", 3));

		System.out.println(MyCalendar.getLastMonthEnd("2016-01-11"));
	}

}
