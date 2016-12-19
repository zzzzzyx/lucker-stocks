package cn.edu.nju.luckers.webserver.main.stock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.KGraphService;
import cn.edu.nju.luckers.calculate_center.nightfactory.ScoreTask;
import cn.edu.nju.luckers.calculate_center.po.KGraphPO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StockDataServlet extends HttpServlet {

	/**
	 * 获取股票日k图的数据
	 */
	private static final long serialVersionUID = 1L;
	KGraphService kService = LogicController.getKGraphService();
	ScoreTask scoreTask;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		
		System.out.println("requested id is "+id);
		PrintWriter out = response.getWriter();

		ArrayList<KGraphPO> poList = kService.getDayGraph(id);
		JSONArray array = new JSONArray();
		JSONObject member = null;

		for (int i = 0; i < poList.size(); i++) {
			KGraphPO po = poList.get(i);
			member = new JSONObject();
			String date = po.getDate();
			double open = Double.parseDouble(po.getOpen());
			double close = Double.parseDouble(po.getClose());
			double high = Double.parseDouble(po.getHigh());
			double low = Double.parseDouble(po.getLow());
			String vol = po.getVolume();
			member.put("date", "'" + (date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6)) + "'");
			member.put("openprice", open);
			member.put("closeprice", close);
			member.put("high", high);
			member.put("low", low);
			member.put("vol", Long.parseLong(vol)/10000);
			array.add(member);
		}
 
		out.println(array);
		out.flush();
		out.close();

	}

}
