package cn.edu.nju.luckers.calculate_center.businesslogic.impl;

import java.io.IOException;
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.businesslogic.service.NewsService;
import cn.edu.nju.luckers.calculate_center.data.DataController;
import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.service.NewsDataGetter;
import cn.edu.nju.luckers.calculate_center.po.NewsPO;

public class NewsServer implements NewsService{
	
	private NewsDataGetter dataGetter;
	
	public NewsServer(){
		this.dataGetter = DataController.getNewDataGetter();
	}

	public ArrayList<NewsPO> getNews(String id) {
		ArrayList<NewsPO> pos = new ArrayList<NewsPO>();
		
		try {
			pos = dataGetter.getNews(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		return pos;
	}

}
