package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.ScoreCalServiceImpl;

import java.util.ArrayList;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ScoreCalService.StockScoreService;
import cn.edu.nju.luckers.luckers_stocks.vo.IndustryScoreVO;
import cn.edu.nju.luckers.luckers_stocks.vo.IndustryVO;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
/**
 * 行业得分计算器
 * @author NjuMzc
 *
 */
public class IndustryScoreCalculator {

    StockDetailService dataService;
    StockScoreService stockScore;
	
	public IndustryScoreCalculator(StockDetailService dataService){
		this.dataService = dataService;
		this.stockScore = new StockScoreServiceImpl(dataService);
	}
	
	
	public IndustryScoreVO calculate(IndustryVO vo,String start,String end){
        //先计算每只股票的市场额
	    ArrayList<String> idList = vo.getList();
	    ArrayList<Double> stockSums = new ArrayList<>();
	    
	    double sum = 0;
	    
	    for(int i = 0;i<idList.size();i++){
	    	String id = idList.get(i);
		    StockInformVO oneDay = dataService.getOneDay(id, "2016-04-05");
		    double turnover = Double.valueOf(oneDay.getTurnover())/100;
		    double Volume = Double.valueOf(oneDay.getVolume());
		    
		    //如果出现了当天这只股票毛都没卖出去的情况，，，
		    //妈的怎么可能，先不管这个了，之后再考虑
		    if(turnover == 0){
		    	stockSums.add(0.0);
		    	continue;
		    }else{
		    	double stockSum = Volume/turnover;
		    	stockSums.add(stockSum);
			    sum+=stockSum;
		    } 
	    }
	    //根据市场占有加权
	    double risk=0;//15
	    double ben=0;//40
	    double mark=0;//10
	    double pros=0;//15
	    double con=0;//20
	    
	    double score = 0;
	    
	    for(int i=0;i<idList.size();i++){
	    	
	    	risk += (double)stockScore.calRisk(idList.get(i), start, end)*((double)stockSums.get(i)/sum);
	        ben += (double)stockScore.calBenfit(idList.get(i), start, end)*((double)stockSums.get(i)/sum);
	        mark += (double)stockScore.calMarket(idList.get(i), start, end)*((double)stockSums.get(i)/sum);
	        pros += (double)stockScore.calProspect(idList.get(i), start, end)*((double)stockSums.get(i)/sum);
	        con += (double)stockScore.calConsistency(idList.get(i), start, end)*((double)stockSums.get(i)/sum);
	    }
	     
	    score = risk*0.15 + ben *0.45 + mark*0.1+pros*0.10+con*0.2;
	    double[] scores = {risk,ben,mark,pros,con,score};
	    
	    IndustryScoreVO result = new IndustryScoreVO(scores);
		
		return result;
	}
	
	
}
