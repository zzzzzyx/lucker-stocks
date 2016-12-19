package cn.edu.nju.luckers.calculate_center.nightfactory;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class NightFactory implements ServletContextListener {

	private Timer timer;
	private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		event.getServletContext().log("定时器已销毁");
	}

	public void contextInitialized(ServletContextEvent event) {
		timer = new Timer();
		event.getServletContext().log("timer begin!");
		// 设置每晚00:02分执行任务
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 2);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();

		timer.schedule(ScoreTask.getInstance(), date, PERIOD_DAY);
		timer.schedule(BayesTask.getInstance(), date, PERIOD_DAY);

		event.getServletContext().log("add to the schedule list!");
	}

}
