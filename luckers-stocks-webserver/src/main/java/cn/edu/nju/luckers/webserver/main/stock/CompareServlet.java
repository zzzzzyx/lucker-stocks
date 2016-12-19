package cn.edu.nju.luckers.webserver.main.stock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.impl.CompareMarketServer;
import cn.edu.nju.luckers.calculate_center.vo.MarketCompareVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CompareServlet extends HttpServlet {

	/**
	 * 用于股票和大盘对比的servlet
	 */
	private static final long serialVersionUID = 1L;
	private CompareMarketServer service;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("stockID");
		service = (CompareMarketServer) LogicController.getMarketCompareService();
		JSONArray array = new JSONArray();
		JSONObject member = null;

		ArrayList<MarketCompareVO> voList = service.compareMarket(id);
		for (int i = 0; i < voList.size(); i++) {
			MarketCompareVO vo = voList.get(i);
			member = new JSONObject();
			member.put("date", vo.getDate());
			member.put("market", vo.getMarket());
			member.put("stock", vo.getStock());
			array.add(member);
		}

		out.println(array);
		out.flush();
		out.close();

	}

}
