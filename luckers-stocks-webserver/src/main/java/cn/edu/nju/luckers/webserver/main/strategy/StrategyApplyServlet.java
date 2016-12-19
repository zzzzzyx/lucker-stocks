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
import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.StrategyService;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;
import net.sf.json.JSONArray;

public class StrategyApplyServlet extends HttpServlet {

	/**
	 * 策略应用的servlet
	 */
	private static final long serialVersionUID = 1L;
	MyStrategyService service;
	StrategyService dataservice;
	Strategy strategy;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();

		String strategyID = request.getParameter("sidd");
		Long id = Long.parseLong(strategyID);
		String username = request.getParameter("username");
		service = new MyStrategyServer();
		JSONArray array = null;
		try {
			dataservice = (StrategyService) RMIService.getInstance(StrategyService.class);
			strategy=dataservice.getOneStrategy(new UserIdentity(username, "123456"), id);
			array = service.applyOnHistory(strategy);
		} catch (NotBoundException e) {
			array=new JSONArray();
		}
		
		out.println(array);
		out.flush();
		out.close();
	}

}
