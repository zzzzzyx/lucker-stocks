package cn.edu.nju.luckers.webserver.main.strategy;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.impl.MyStrategyServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.MyStrategyService;
import cn.edu.nju.luckers.calculate_center.global.MyCalendar;
import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.StrategyService;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;
import net.sf.json.JSONObject;

public class StrategyVertifyServlet extends HttpServlet {

	/**
	 * 验证策略合理性servlet
	 */
	private static final long serialVersionUID = 1L;
	MyStrategyService service;
	StrategyService dataservice;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service = new MyStrategyServer();
		JSONObject json = new JSONObject();

		String stockName = request.getParameter("stockName");
		String strategyName = request.getParameter("strategyName");
		String username = request.getParameter("username");
		String stockId = request.getParameter("stockId");

		// String startDate = request.getParameter("startDate");
		// String endDate = request.getParameter("endDate");
		String startDate = MyCalendar.getToday();
		String endDate = MyCalendar.getToday();
		String type = request.getParameter("type");
		String days = request.getParameter("days");
		System.out.println("type=" + type);
		System.out.println("days=" + days);
		if (type.equals("应用在过去")) {
			startDate = MyCalendar.getDayByNum(Integer.parseInt(days));
		} else if (type.equals("应用在未来")) {
			endDate = MyCalendar.getDayByNum(-Integer.parseInt(days));
		}

		String today = MyCalendar.getToday();

		String buyIn = request.getParameter("buyIn");
		String sellOut = request.getParameter("sellOut");
		String buy[] = buyIn.split(",");
		double in[] = new double[10];
		String sell[] = sellOut.split(",");
		double out[] = new double[10];
		for (int i = 0; i < 10; i++) {
			if (buy[i] == null) {
				if (i % 2 == 0) {
					in[i] = 0;
				} else {
					in[i] = Double.MAX_VALUE;
				}
			} else {
				in[i] = Double.parseDouble(buy[i]);
			}

			if (sell[i] == null) {
				if (i % 2 == 0) {
					out[i] = 0;
				} else {
					out[i] = Double.MAX_VALUE;
				}
			} else {
				out[i] = Double.parseDouble(sell[i]);
			}
		}
		Strategy s_tobe = new Strategy(stockId, today, startDate, endDate, 0.0, in[0], in[1], in[2] * 10000,
				in[3] * 10000, in[4], in[5], in[6], in[7], in[8], in[9], out[0], out[1], out[2] * 10000, out[3] * 10000,
				out[4], out[5], out[6], out[7], out[8], out[9], strategyName, stockName,username);
		boolean isOK = service.vertify(s_tobe);
		if (isOK) {
			try {
				dataservice = (StrategyService) RMIService.getInstance(StrategyService.class);

				boolean isSuccessDatabase = dataservice.createStrategy(new UserIdentity(username, "123456"), s_tobe);
				if (isSuccessDatabase) {
					json.put("id", s_tobe.getId());
				}
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		}

		response.setContentType("text/html;charset=gbk");
		PrintWriter outP = response.getWriter();
		if (isOK) {
			json.put("isOK", "true");
		} else {
			json.put("isOK", "false");
		}
		outP.println(json);
		outP.flush();
		outP.close();
	}

}
