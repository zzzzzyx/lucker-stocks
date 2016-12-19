package cn.edu.nju.luckers.webserver.main.news;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.nju.luckers.calculate_center.businesslogic.impl.NewsServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.NewsService;
import cn.edu.nju.luckers.calculate_center.po.NewsPO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NewsServlet extends HttpServlet {

	/**
	 * 获得新闻的servlet
	 */
	private static final long serialVersionUID = 1L;
	NewsService service=new NewsServer();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/html;charset=gbk");
		 PrintWriter out=response.getWriter();
		 
		 String stockId=request.getParameter("stockID");
		 ArrayList<NewsPO> newsPOList=service.getNews(stockId );
		 
		 JSONObject json=new JSONObject();
		 JSONArray array=new JSONArray();
		 JSONObject member=null;
		 
		 for(int i=0;i<newsPOList.size();i++){
			 NewsPO po=newsPOList.get(i);
			 member=new JSONObject();
			 String title=po.getTitle();
			 String url=po.getUrl();
			 String date=po.getDate();
			 
			 member.put("date", date);
			 member.put( "stockUrl", url);
			 member.put("newsTitle", title);
			 
			 array.add(member);
		 }
		 
		 json.put("jsonArray", array);
		 out.println(array);
		 out.flush();
		 out.close();
	}

}
