package cn.edu.nju.luckers.calculate_center;

import cn.edu.nju.luckers.calculate_center.businesslogic.impl.MyStrategyServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.MyStrategyService;
import cn.edu.nju.luckers.calculate_center.global.MyCalendar;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;
import net.sf.json.JSONArray;

public class MyStrategyOnHistoryTest {

	public static void main(String[] arg) {

		String today = MyCalendar.getToday();
		String hundredDaysAgo = MyCalendar.getDayByNum(50);
		String fiveDaysAgo = MyCalendar.getDayByNum(5);

		MyStrategyService service = new MyStrategyServer();
		Strategy s = new Strategy("sh600015", today, hundredDaysAgo, fiveDaysAgo, 0, 9.0, 10.5, 7000000,
				Double.MAX_VALUE, 0.0, Double.MAX_VALUE, 0.0, Double.MAX_VALUE, 0.0, Double.MAX_VALUE, 11.0, 13.0, 0.0,
				8000000, 0.0, Double.MAX_VALUE, 0.0, Double.MAX_VALUE, 0.0, Double.MAX_VALUE,"123","123","john");
		JSONArray array = service.applyOnHistory(s);
		for (int i = 0; i < array.size(); i++) {
			System.out.println(array.get(i));
		}
		System.out.println("benefit:"+s.getBenefit());
	}

}
