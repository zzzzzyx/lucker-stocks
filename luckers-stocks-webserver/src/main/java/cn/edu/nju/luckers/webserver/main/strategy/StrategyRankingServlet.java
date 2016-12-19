package cn.edu.nju.luckers.webserver.main.strategy;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.impl.MyStrategyServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.MyStrategyService;
import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.StrategyService;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StrategyRankingServlet extends HttpServlet {
	StrategyService service;
	private MyStrategyService myStrategyService;

	/**
	 * 策略收益排行榜servlet
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest requset, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		JSONArray array = new JSONArray();
		JSONObject member = null;
		try {
			service = (StrategyService) RMIService.getInstance(StrategyService.class);
		} catch (NotBoundException e) {
		}
		myStrategyService = new MyStrategyServer();

		List<Strategy> allStrategy = service.getAllStrategy();
		Vector<Long> v = new Vector<Long>();
		Vector<String> name = new Vector<String>();
		
		System.out.println("allStrategy==null ? "+allStrategy==null);
		
		if (allStrategy != null) {
			for (int i = 0; i < allStrategy.size(); i++) {
				Strategy s = allStrategy.get(i);
				myStrategyService.applyOnHistory(s);
				if (s.getBenefit() > 3000) {
					v.add(s.getId());
					name.add(s.getUserName());
				}
			}

			for (int i = 0; i < v.size(); i++) {
				member = new JSONObject();
				Long num = v.get(i);
				Strategy s = service.getOneStrategy(new UserIdentity(name.get(i), "123456"), num);
				myStrategyService.applyOnHistory(s);
				member.put("userName", s.getUserName());
				member.put("stockname", s.getStockName());
				member.put("stockid", s.getStockId());
				member.put("sname", s.getStrategyName());
				member.put("benefit", s.getBenefit()+"元");
				array.add(member);
			}
		}
		System.out.println("ranking is here!" + array);
		out.println(array);
		out.flush();
		out.close();
	}

}
