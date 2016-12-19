package cn.edu.nju.luckers.calculate_center.businesslogic.impl;

import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.businesslogic.LogicController;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.CompareMarketService;
import cn.edu.nju.luckers.calculate_center.businesslogic.service.KGraphService;
import cn.edu.nju.luckers.calculate_center.po.KGraphPO;
import cn.edu.nju.luckers.calculate_center.vo.MarketCompareVO;

public class CompareMarketServer implements CompareMarketService {

	KGraphService kService;
	
	public CompareMarketServer(){
		this.kService = LogicController.getKGraphService();
	}
	
	
	public ArrayList<MarketCompareVO> compareMarket(String id) {
		
		ArrayList<MarketCompareVO> result = new ArrayList<MarketCompareVO>();
		
		String mark = id.substring(0, 2);
		
		String market;
		if(mark.equals("sh")){
			market = "sh000001";
		}else{
			market = "sz399001";
		}
		
		ArrayList<KGraphPO> markets = kService.getDayGraph(market);
		ArrayList<KGraphPO> stocks = kService.getDayGraph(id);
		
		if(stocks == null){
			//说明股票代号错误
			return null;
		}
		int ptr = 0;
		
		for(int i =0;i< markets.size();i++){
			if(ptr>=stocks.size()){
				break;
			}
			KGraphPO marketOne = markets.get(i);
			KGraphPO stockOne = stocks.get(ptr);
			
			if(stockOne.getDate().equals(marketOne.getDate())){
				MarketCompareVO vo = new MarketCompareVO(stockOne.getDate(), marketOne.getClose(),
						stockOne.getClose());
				result.add(vo);
				ptr ++;
				continue;
			}else{
				continue;
			}
		}
		
		return result;
	}

}
