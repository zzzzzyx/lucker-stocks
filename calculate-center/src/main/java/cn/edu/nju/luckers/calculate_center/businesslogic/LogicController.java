package cn.edu.nju.luckers.calculate_center.businesslogic;

import cn.edu.nju.luckers.calculate_center.businesslogic.impl.CompareMarketServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.impl.IndusrtyFlowServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.impl.KGraphServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.impl.NewsServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.impl.StockInformServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.impl.StockScoreServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.impl.KReportServer.KReportServer;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.CompareMarketService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.IndustryFlowService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.KGraphService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.KReportService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.NewsService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockScoreService;

public class LogicController {

	private static StockInformService informService = null;
	
	private static KGraphService kGraphService = null;
	
	private static NewsService newsService = null;
	
	private static StockScoreService scoreService = null;
	
	private static IndustryFlowService industryFlowService = null;
	
	private static KReportService kReportService = null;
	
	private static CompareMarketService compareMarketService = null;
	
	public static StockInformService getInformService(){
		if(informService == null){
			informService = new StockInformServer();			
		}
		return informService;
	}
	
	public static KGraphService getKGraphService(){
		if(kGraphService == null){
			kGraphService = new KGraphServer();			
		}
		return kGraphService;
	}
	
	public static NewsService getNewsService(){
		if(newsService == null){
			newsService = new NewsServer();
		}
		return newsService;
	}
	
	public static StockScoreService getScoreService(){
		if(scoreService == null){
			scoreService = new StockScoreServer();
		}
		return scoreService;
	}
	
	public static IndustryFlowService getFlowService(){
		if(industryFlowService == null){
			industryFlowService = new IndusrtyFlowServer();
		}
		return industryFlowService;
	}
	
	public static KReportService getKReporter(){
		if(kReportService == null){
			kReportService = new KReportServer();
		}
		return kReportService;
	}
	
	public static CompareMarketService getMarketCompareService(){
		if(compareMarketService == null){
			compareMarketService = new CompareMarketServer();
		}
		return compareMarketService;
	}
}
