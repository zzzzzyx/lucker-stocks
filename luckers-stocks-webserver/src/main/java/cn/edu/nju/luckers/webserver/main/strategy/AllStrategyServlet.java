package cn.edu.nju.luckers.webserver.main.strategy;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

public class AllStrategyServlet extends HttpServlet {

	/**
	 * 显示用户所有策略的servlet
	 */
	private static final long serialVersionUID = 1L;
	private StrategyService service;
	private MyStrategyService myStrategyService;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private Date today=new Date();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		JSONArray array = new JSONArray();
		JSONObject member;
		List<Strategy> allStrategy = null;

		String username = request.getParameter("username");

		try {
			service = (StrategyService) RMIService.getInstance(StrategyService.class);
			allStrategy = service.getStrategy(new UserIdentity(username, "123456"));

		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		myStrategyService = new MyStrategyServer();

		if (allStrategy != null) {
			for (int i = 0; i < allStrategy.size(); i++) {
				Strategy s = allStrategy.get(i);
				member = new JSONObject();
				
				String end=s.getApply_end();
				Date end_date;
				try {
					 end_date=sdf.parse(end);
				} catch (ParseException e) {
					end_date=new Date();
				}
				if(today.before(end_date)){
					member.put("ifApply", "not");
				}else{
					member.put("ifApply", "over");
				}
				
				member.put("straName", s.getStrategyName());
				member.put("stockName", s.getStockName());
				member.put("stockid", s.getStockId());
				member.put("apply_end", s.getApply_end());
				member.put("make_date", s.getMake_date());
				member.put("apply_start", s.getApply_start());
				member.put("s_id", s.getId());

				myStrategyService.applyOnHistory(s);
				member.put("benefit", s.getBenefit()+"元");
				array.add(member);
			}
		}

		out.println(array);
		out.flush();
		out.close();
	}

}
