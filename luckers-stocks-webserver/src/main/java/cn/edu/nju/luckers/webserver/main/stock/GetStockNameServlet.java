package cn.edu.nju.luckers.webserver.main.stock;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import cn.edu.nju.luckers.calculate_center.vo.StockImVO;
import net.sf.json.JSONObject;

public class GetStockNameServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	StockInformService service;
	DecimalFormat df = new DecimalFormat("0.00");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");

		service = LogicController.getInformService();
		String id = request.getParameter("stockID");
		
		System.out.println("getName: "+id);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=gbk");

		JSONObject member = new JSONObject();
		String name = service.getName(id);
		String career = service.getIndustry(id);
		ArrayList<StockImVO> latestInfor = service.getImmediate(id);
		String latestPrice = latestInfor.get(latestInfor.size() - 1).getPrice();
		String latestRf = latestInfor.get(latestInfor.size() - 1).getRf();

		member.put("latestPrice", latestPrice + "元");
		member.put("latestRf", df.format(Double.parseDouble(latestRf) * 100) + "%");
		member.put("sName", name);
		member.put("career", career);
 
		out.println(member);
		out.flush();
		out.close();

	}

}
