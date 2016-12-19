package cn.edu.nju.luckers.webserver.main.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import cn.edu.nju.luckers.calculate_center.nightfactory.BayesTask;
import cn.edu.nju.luckers.calculate_center.vo.StockImVO;
import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.favorites.FavoriteService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserServlet extends HttpServlet {

	/**
	 * 用于收藏列表的信息传递
	 */
	private static final long serialVersionUID = 1L;
	FavoriteService service;
	StockInformService stockService;
	BayesTask bayesTask;
	DecimalFormat df = new DecimalFormat("0.00");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		bayesTask = BayesTask.getInstance();
		try {
			service = (FavoriteService) RMIService.getInstance(FavoriteService.class);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		stockService = LogicController.getInformService();

		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");

		ArrayList<String> userFavoriteList = service.getUserFavorites(new UserIdentity(username, "123456"));

		JSONArray array = new JSONArray();
		JSONObject member = null;

		if (userFavoriteList != null) {
			for (int i = 0; i < userFavoriteList.size(); i++) {
				ArrayList<StockImVO> latestInfor = stockService.getImmediate(userFavoriteList.get(i));
				String latestPrice = latestInfor.get(latestInfor.size() - 1).getPrice();
				String latestRf = latestInfor.get(latestInfor.size() - 1).getRf();

				String stockId = userFavoriteList.get(i);
				String name = stockService.getName(stockId);
				String career = stockService.getIndustry(stockId);
				String forecast = bayesTask.getForecast(stockId);

				member = new JSONObject();
				member.put("stockId", stockId);
				member.put("stockName", name);
				member.put("career", career);
				member.put("forecast", forecast);
				member.put("latestPrice", latestPrice + "元");
				Double rf = Double.parseDouble(latestRf);
				member.put("latestRf", df.format(rf * 100) + "%");
				if (rf > 0) {
					member.put("remindUD", "red");
				} else {
					member.put("remindUD", "green");
				}
				array.add(member);
			}
		}
		out.println(array);
		out.flush();
		out.close();

	}

}
