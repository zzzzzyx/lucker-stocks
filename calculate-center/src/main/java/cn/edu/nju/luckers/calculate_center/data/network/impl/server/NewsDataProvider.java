package cn.edu.nju.luckers.calculate_center.data.network.impl.server;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.impl.networkTools.JavaUrlConnecter;
import cn.edu.nju.luckers.calculate_center.data.network.service.NewsDataGetter;
import cn.edu.nju.luckers.calculate_center.po.NewsPO;

public class NewsDataProvider implements NewsDataGetter {

	public ArrayList<NewsPO> getNews(String id) throws IOException, ConnectionFailure {
		String url = "http://finance.sina.com.cn/realstock/company/"+id+"/nc.shtml";
		String webinfo = JavaUrlConnecter.getURLContent(url, "gbk");
		
		ArrayList<NewsPO> list = new ArrayList<NewsPO>();
		
		
		if(id.equals("sh000001")||id.equals("sz399001")){
			//爬取大盘部分的数据
			String[] ss1 = webinfo.split("cjyw");
			String[] ss2 = ss1[1].split("<ul>");
			String[] ss3 = ss2[1].split("<li>");
			
			
			for(int i=1;i<ss3.length;i++){
				String info = ss3[i];
				String[] dateInfo = info.split("<span>\\(");
				String date = dateInfo[1].substring(0, 5);
				String[] urlInfo = info.split("<a href=")[1].split("\"");
				String thisurl = urlInfo[1];
				thisurl = "http://finance.sina.com.cn"+thisurl;
				String[] titleinfo = info.split("title=\"")[1].split("\"");
				String title = titleinfo[0];
				
				NewsPO po = new NewsPO(title, date, thisurl);
				list.add(po);
			}
			
		}else{
			String[] ss1 = webinfo.split("stockNews");
			String[] ss2 = ss1[1].split("<ul>");
			String[] ss3 = ss2[1].split("<li>");
			
			
			for(int i=1;i<ss3.length;i++){
				String info = ss3[i];
				
				String[] dateInfo = info.split("<span>\\(");
				String date = dateInfo[1].substring(0, 5);
				String[] urlInfo = info.split("<a href=")[1].split("\"");
				String thisurl = urlInfo[1];
				String[] titleinfo = info.split("target=\"_blank\">")[1].split("</a>");
				String title = titleinfo[0];
				
				NewsPO po = new NewsPO(title, date, thisurl);
				list.add(po);
			}
		}

		return list;
	}

}
