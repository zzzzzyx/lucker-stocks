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
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;
import net.sf.json.JSONObject;

public class StrategyShowServlet extends HttpServlet {

	/**
	 * 用于显示策略信息的servlet
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

		String id = request.getParameter("sid");
		String name = request.getParameter("userName");
		Strategy s = null;
		try {
			dataservice = (StrategyService) RMIService.getInstance(StrategyService.class);
			s = dataservice.getOneStrategy(new UserIdentity(name, "123456"), Long.parseLong(id));
		} catch (NotBoundException e) {

		}

		JSONObject member = new JSONObject();
		if(s!=null){
			member.put("a", s.getPrice_in_low());
			member.put("b", s.getVolume_in_low());
			member.put("c", s.getTurnover_in_low());
			member.put("d", s.getPe_in_low());
			member.put("e", s.getPb_in_low());
			member.put("f", s.getPrice_in_high());
			member.put("g", s.getVolume_in_high());
			member.put("h", s.getTurnover_in_high());
			member.put("i", s.getPe_in_high());
			member.put("j", s.getPb_in_high());
			member.put("k", s.getPrice_out_low());
			member.put("l", s.getVolume_out_low());
			member.put("m", s.getTurnover_out_low());
			member.put("n", s.getPe_out_low());
			member.put("o", s.getPb_out_low());
			member.put("p", s.getPrice_out_high());
			member.put("q", s.getVolume_out_high());
			member.put("r", s.getTurnover_out_high());
			member.put("s", s.getPe_out_high());
			member.put("t", s.getPb_out_high());
		}

		out.println(member);
		out.flush();
		out.close();
	}

}
