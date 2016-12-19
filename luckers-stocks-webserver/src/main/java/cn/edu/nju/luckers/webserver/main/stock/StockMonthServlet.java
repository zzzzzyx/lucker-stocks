package cn.edu.nju.luckers.webserver.main.stock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.impl.KGraphServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.KGraphService;
import cn.edu.nju.luckers.calculate_center.po.KGraphPO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StockMonthServlet extends HttpServlet {

	/**
	 * 获取月k图的servlet
	 */
	private static final long serialVersionUID = 1L;
	KGraphService kService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String id = req.getParameter("id");

		kService=new KGraphServer();
		System.out.println("requested id is " + id);
		ArrayList<KGraphPO> poList = kService.getMonGraph(id);
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
			member.put("date",
					"'" + (date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6)) + "'");
			member.put("openprice", open);
			member.put("closeprice", close);
			member.put("high", high);
			member.put("low", low);
			member.put("vol", Long.parseLong(vol)/10000);
			array.add(member);
		}

		System.out.println(array);
		out.println(array);
		out.flush();
		out.close();
	}

}
