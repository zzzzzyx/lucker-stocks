package cn.edu.nju.luckers.webserver.main.career;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.IndustryFlowService;
import cn.edu.nju.luckers.calculate_center.vo.IndustryFlowVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 

public class CareerServlet extends HttpServlet {

	/**
	 * 行业资金流向图的数据传递
	 */
	private static final long serialVersionUID = 1L;
	IndustryFlowService service;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();

		service = LogicController.getFlowService();
		ArrayList<IndustryFlowVO> voList = service.getIndustryFlow();

		JSONArray array = new JSONArray();
		JSONObject member = null;

		for (int i = 0; i < voList.size(); i++) {

			IndustryFlowVO this_Vo = voList.get(i);
			member = new JSONObject();
			member.put("Cname", this_Vo.getName());
			member.put("flow", this_Vo.getFlow());
			
			if(Double.parseDouble(this_Vo.getFlow()) >0 ){
				member.put("remind", "in");
			}else{
				member.put("remind", "out");
			}

			array.add(member);
		}
 
		out.println(array);
		out.flush();
		out.close();

	}

}
