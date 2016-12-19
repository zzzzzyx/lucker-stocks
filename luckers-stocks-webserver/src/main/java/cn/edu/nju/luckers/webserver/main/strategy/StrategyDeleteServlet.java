package cn.edu.nju.luckers.webserver.main.strategy;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.StrategyService;
import net.sf.json.JSONObject;

public class StrategyDeleteServlet extends HttpServlet {

	/**
	 * 删除策略servlet
	 */
	private static final long serialVersionUID = 1L;
	StrategyService dataservice;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();

		String strategyID = request.getParameter("sid");
		Long id = Long.parseLong(strategyID);
		String username = request.getParameter("username");
		boolean isDeteled = false;

		try {
			dataservice = (StrategyService) RMIService.getInstance(StrategyService.class);
			if (dataservice.deleteStrategy(new UserIdentity(username, "123456"), id)) {
				isDeteled = true;
			}
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isDeteled) {
			json.put("Dstate", "yes");
		} else {
			json.put("Dstate", "no");
		}
		out.println(json);
		out.flush();
		out.close();
	}

}
