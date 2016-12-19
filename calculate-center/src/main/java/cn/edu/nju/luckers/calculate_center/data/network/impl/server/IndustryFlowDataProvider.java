package cn.edu.nju.luckers.calculate_center.data.network.impl.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.impl.networkTools.JavaUrlConnecter;
import cn.edu.nju.luckers.calculate_center.data.network.service.IndustryFlowDataGetter;
import cn.edu.nju.luckers.calculate_center.vo.IndustryFlowVO;

public class IndustryFlowDataProvider implements IndustryFlowDataGetter {

	
	public ArrayList<IndustryFlowVO> getIndustryFlow() throws IOException, ConnectionFailure {
		String url = "http://data.eastmoney.com/bkzj/rank/hy/alljson.html";
		String webinfo = JavaUrlConnecter.getURLContent(url, "UTF-8");
		
		ArrayList<IndustryFlowVO> list = new ArrayList<IndustryFlowVO>();
		
		String info  = webinfo.substring(20);
		ArrayList<String> ll = new ArrayList<String>();
		
		Pattern p = Pattern.compile("\\{\".*\"\\}");
		Matcher matcher = p.matcher(info);
		
		while(matcher.find()){
			String s = matcher.group();
			ll.add(s);
		}
		
		String t[] = ll.get(0).split("\\{");
		
		for(int i =1;i< t.length;i++){
			String name = t[i].split(":\"")[1].split("\"")[0];
			String flow = t[i].split("value\":\"")[1].split("\"")[0];
			IndustryFlowVO vo =new IndustryFlowVO(name, flow, false);
			list.add(vo);
		}
		return list;
	}

}
