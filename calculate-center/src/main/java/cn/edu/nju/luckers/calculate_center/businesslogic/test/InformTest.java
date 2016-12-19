package cn.edu.nju.luckers.calculate_center.businesslogic.test;

import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.CompareMarketService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.KGraphService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.NewsService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockInformService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockScoreService;
import cn.edu.nju.luckers.calculate_center.global.DataKey;
import cn.edu.nju.luckers.calculate_center.global.MyCalendar;
import cn.edu.nju.luckers.calculate_center.global.exception.HaltException;
import cn.edu.nju.luckers.calculate_center.po.KGraphPO;
import cn.edu.nju.luckers.calculate_center.po.NewsPO;
import cn.edu.nju.luckers.calculate_center.vo.MarketCompareVO;
import cn.edu.nju.luckers.calculate_center.vo.StockImVO;
import cn.edu.nju.luckers.calculate_center.vo.StockInformVO;
import cn.edu.nju.luckers.calculate_center.vo.StockScoreVO;

public class InformTest {

	public static void main(String[] args) {
//         KGraphService service = LogicController.getKGraphService();
//        
//        NewsService newsService = LogicController.getNewsService();
 
//        StockScoreService service = LogicController.getScoreService();
        
//        StockScoreVO vo = null;
//		try {
//			vo = service.getScore("sz300431" );
//		} catch (HaltException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//        System.out.println(vo.getScore("risk"));
        
//       StockInformVO vo = service.getInform("sz300251", "2017-05-07");
        
//        ArrayList<NewsPO> vos = newsService.getNews("sh600002");
//        for(int i=0;i<vos.size();i++){
//        	StockInformVO vo = vos.get(i);
//        	System.out.println(vo.getItem(DataKey.open)+" "+vo.getItem(DataKey.date));
//        }
		
//		KGraphService service= LogicController.getKGraphService();
//		ArrayList<KGraphPO> list1 =service.getDayGraph("sz300182");
//		ArrayList<KGraphPO> list2 =service.getDayGraph("sz399001");
//		
//		for(int i =0;i<Math.min(list1.size(), list2.size());i++){
//			System.out.println("个股 "+list1.get(i).getDate()+" "+list1.get(i).getClose());
//			System.out.println("大盘 "+list2.get(i).getDate()+" "+list2.get(i).getClose());
//		}
		
//		CompareMarketService service = LogicController.getMarketCompareService();
//		ArrayList<MarketCompareVO> list = service.compareMarket("sh600000");
//		
//		for(int i=0;i<list.size();i++){
//			MarketCompareVO vo = list.get(i);
//			
//			System.out.println(vo.getDate()+" "+vo.getMarket()+" "+vo.getStock());
//		}
		
		StockInformService service = LogicController.getInformService();
//		StockInformVO vo = service.getInform("sz300182", "2016-06-16");
		String name = service.getName("sajdiodj");
//		for(int i =0;i<list.size();i++){
////		System.out.println(list.get(i).getRf()+" "+list.get(i).getPrice());
//	}
		System.out.println(name);
	}

}
