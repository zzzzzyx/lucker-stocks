package cn.edu.nju.luckers.luckers_stocks.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ZyxCalender {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	public static void main(String[] args) {
		System.out.println(leftIsSmallerOrEqual("2015-03-02", "2015-03-02"));
	}
	public static String getDaysAgo(String day, int daysAgo){
		Date date=null;  
		try {
			date = format.parse(day);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.DAY_OF_MONTH, -daysAgo);
		return format.format(cal.getTime());		
	}
	
	public static boolean leftIsSmallerOrEqual(String left,String right){
		Date leftDate=null;  
		Date rightDate=null;  
		try {
			leftDate = format.parse(left);
			rightDate = format.parse(right);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return !leftDate.after(rightDate);
	}
}
