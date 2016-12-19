package cn.edu.nju.luckers.webserver.main.score;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.KGraphService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.KReportService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockScoreService;
import cn.edu.nju.luckers.calculate_center.global.exception.HaltException;
import cn.edu.nju.luckers.calculate_center.nightfactory.ScoreTask;
import cn.edu.nju.luckers.calculate_center.vo.KReportVO;
import cn.edu.nju.luckers.calculate_center.vo.StockScoreVO;
import net.sf.json.JSONObject;

public class ScoreServlet extends HttpServlet {

	/**
	 * 雷达图评分和文本分析数据传递servlet
	 */
	private static final long serialVersionUID = 1L;
	ScoreTask scoreTask;
	StockScoreService scoreService;
	KReportService serviceOfReport;
	KGraphService serviceOfKGragh;
	DecimalFormat df = new DecimalFormat("0.0");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gbk");

		scoreTask = ScoreTask.getInstance();
		serviceOfReport = LogicController.getKReporter();
		serviceOfKGragh = LogicController.getKGraphService();
		scoreService=LogicController.getScoreService();

		JSONObject member = new JSONObject();
		String id = request.getParameter("id");

		PrintWriter out = response.getWriter();
		System.out.println("id=" + id);
		StockScoreVO vo = scoreTask.getTargetStockVO(id);

		if (vo == null) {
			try {
				vo = scoreService.getScore(id);
			} catch (HaltException e) {
				 System.out.println("停牌");
			}
		}

		KReportVO kReportVo = serviceOfReport.getReport(serviceOfKGragh.getDayGraph(id));

		String risk = vo.getScore("risk");
		String benefit = vo.getScore("benefit");
		String market = vo.getScore("market");
		String prospect = vo.getScore("prospect");
		String consistency = vo.getScore("consistency");
		member.put("benefit", df.format(Double.parseDouble(benefit)));// 盈利性
		member.put("market", df.format(Double.parseDouble(market)));// 市场性
		member.put("prospect", df.format(Double.parseDouble(prospect)));// 前景性
		member.put("consistency", df.format(Double.parseDouble(consistency)));// 稳定性
		member.put("risk", df.format(Double.parseDouble(risk)));// 风险性
		member.put("report", kReportVo.getReport());

		out.print(member);
		out.flush();
		out.close();

	}

}
