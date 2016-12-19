package cn.edu.nju.luckers.webserver.main.recommend;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockScoreService;
import cn.edu.nju.luckers.calculate_center.global.exception.HaltException;
import cn.edu.nju.luckers.calculate_center.vo.StockScoreVO;
import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.favorites.FavoriteService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RecommendServlet extends HttpServlet {

	/**
	 * 用于推荐股票界面的信息传递
	 */
	private static final long serialVersionUID = 1L;
	FavoriteService service;
	StockScoreService score_service;
	StockInformService infor_service;
	DecimalFormat df = new DecimalFormat("0.00");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			service = (FavoriteService) RMIService.getInstance(FavoriteService.class);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		score_service = LogicController.getScoreService();
		infor_service = LogicController.getInformService();
		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();

		JSONArray array = new JSONArray();
		JSONObject member = null;
		List<String> listOfStock = service.getAllStocks();
		StockScoreVO vo = null;
		for (int i = 0; i < listOfStock.size(); i++) {
			String stockID = listOfStock.get(i);
			try {
				vo = score_service.getScore(stockID);
			} catch (HaltException e) {
				vo = new StockScoreVO();
			}
			String all = df.format(Double.parseDouble(vo.getSum()) * 5);

			if (Double.parseDouble(all) > 80) {
				member = new JSONObject();

				String name = infor_service.getName(stockID);
				String career = infor_service.getIndustry(stockID);

				member.put("score", all);
				member.put("stockId", stockID);
				member.put("stockName", name);
				member.put("career", career);
				array.add(member);
			}
		}
		out.println(array);
		out.flush();
		out.close();
	}

}
