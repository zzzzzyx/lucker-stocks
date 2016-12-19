package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

/**
 * @description 日历，提供静态方法便于日期的操作
 * @author Mzc
 * @date 2016/3/6
 * 
 *       目前可以提供的方法有： getToday（） 获得今天的日期 getYesterday（String
 *       date）获得某一日期昨天的日期，不填date参数则获得昨天日期 getTomorrow（String date）获得某一日期后一天的日期
 *       getTradingDays（String start，String end）获得两个日期之间的交易日天数，不包括终点日期
 *       getNearestTradingDate（） 获得最近的可获得数据的交易日日期
 */
public class MyCalendar {
	
	/**
	 * 获得当前日期所在月的第一天
	 * @param date
	 * @return
	 */
	public static String getMonthStart(String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date day1 = null;
		try {
			day1 = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		// 对 calendar 设置为 date 所定的日期
        
        cal.setTime(day1); 
	        cal.set(Calendar.DAY_OF_MONTH, 1);  
	          
	        return format.format(cal.getTime());  
	}
	
	
	/**
	 * 获得指定日期上个月的月末日期
	 * @param date
	 * @return
	 */
	public static String getLastMonthEnd(String date){
		
		String tempDate = getMonthStart(date);
		
		
        return getDayByNum(tempDate, 1);  
              
	}
	
	/**
	 * 获得最近一个周五交易日;注意，如果当天是周五，则返回上周五，周六则返回周周五
	 * @return
	 */
	public static String getNearestFri(){
		String today = MyCalendar.getToday();
		
		int weekDay = MyCalendar.getWeekDay(today);
		
		String Fri = MyCalendar.getDayByNum((weekDay+2)%7);
		
		return Fri;
	}
	
	
	/**
	 * 通过日期返回周几
	 * 返回值0-6
	 * 如果错误，返回-1
	 * @param date
	 * @return
	 */
	public static int getWeekDay(String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date day = null;
		try {
			day = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		// 对 calendar 设置为 date 所定的日期
		cal.setTime(day);
		
		return cal.get(Calendar.DAY_OF_WEEK)-1;
		
	}

	public static String getToday() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(Calendar.getInstance().getTime());
	}

	public static String getYesterday() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return format.format(cal.getTime());
	}

	public static String getYesterday(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date day = null;
		try {
			day = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		// 对 calendar 设置为 date 所定的日期
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return format.format(cal.getTime());
	}

	public static String getTomorrow(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date day = null;
		try {
			day = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		// 对 calendar 设置为 date 所定的日期
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return format.format(cal.getTime());
	}

	/**
	 * 获得距离今天向前num天的日期，如果num为1就是昨天
	 * 
	 * @param num
	 *            正常情况下是正整数，如果输入0则为当天，输入负数则向后计算
	 * @return 返回的日期形式为yyyy-mm-dd
	 */
	public static String getDayByNum(int num) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1 * num);
		return format.format(cal.getTime());
	}

	public static String getDayByNum(String date, int num) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date day = null;
		try {
			day = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		// 对 calendar 设置为 date 所定的日期
		cal.setTime(day);
		cal.add(Calendar.DAY_OF_MONTH, -1 * num);
		return format.format(cal.getTime());
	}

	public static String getYear() {
		Calendar cal = Calendar.getInstance();
		String year = String.valueOf(cal.get(Calendar.YEAR));
		return year;
	}

	/**
	 * 通过开始与结束日期获得某股票之间的交易天数 注意，不会包括终点日期 简单地说，该方法即返回getDetails方法返回的有效日期
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static ArrayList<String> getTradingDays(String id, String start, String end) {

		ArrayList<String> days = new ArrayList<>();
		Iterator<StockInformVO> voList = LogicController.getInstance().getDetailServer().getDetails(id, start, end);

		while (voList.hasNext()) {
			StockInformVO vo = voList.next();
			days.add(vo.getDate());

		}

		return days;
	}

	public static int getTradingDaysNum(String id, String start, String end) {

		Iterator<StockInformVO> voList = LogicController.getInstance().getDetailServer().getDetails(id, start, end);
		int num = 0;
		while (voList.hasNext()) {
			num++;
		}

		return num;
	}

	/**
	 * 获得最近一天可以获得数据的交易日日期
	 * 
	 * @return 一个标准格式的日期
	 */
	public static String getNearestTradingDate(String id) {
		StockDetailService service = LogicController.getInstance().getDetailServer();

		int num = 0;
		StockInformVO vo = null;
		while (vo == null) {
			vo = service.getOneDay(id, MyCalendar.getDayByNum(num));
			num++;
		}

		return vo.getDate();
	}

	/**
	 * 输入开始日期与日期间所要求的交易日天数，返回满足要求的结束日期 例如，开始日期2016-03-22，length为4，则返回2016-03-28
	 * 尽量别使用这个方法，代价比较大
	 * @param id
	 * @param start
	 * @param length
	 * @return
	 */
	public static String getDateByStartLength(String id, String start, int length) {
		start = MyCalendar.getTomorrow(start);
		String endDate = MyCalendar.getDayByNum(start, -1 * length);
		while (true) {
			if (getTradingDays(id, start, endDate).size() == length) {
				break;
			} else {
				endDate = getDayByNum(endDate, -1);
				// if(endDate.equals(getToday())){
				// return null;
				// }
			}
		}
		endDate = getDayByNum(endDate, 1);
		return endDate;
	}
}
