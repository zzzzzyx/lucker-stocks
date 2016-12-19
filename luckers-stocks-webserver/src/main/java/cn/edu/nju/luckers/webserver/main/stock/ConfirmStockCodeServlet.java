package cn.edu.nju.luckers.webserver.main.stock;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import net.sf.json.JSONObject;

public class ConfirmStockCodeServlet extends HttpServlet {

	/**
	 * 验证代码是否为某只股票的代码
	 */
	private static final long serialVersionUID = 1L;
	StockInformService service;

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
		service= LogicController.getInformService();
		String name = service.getName(id);

		String isOk = "true";
		if (name == null) {
			isOk = "false";
		}

		JSONObject member = new JSONObject();
		member.put("isOk", isOk);
 
		out.println(member);
		out.flush();
		out.close();

	}

}
