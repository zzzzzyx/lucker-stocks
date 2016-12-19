package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.ScoreCalServiceImpl;

import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ScoreCalService.StockScoreService;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class StockScoreServiceImpl implements StockScoreService {
	
	StockDetailService dataService;
	
	public StockScoreServiceImpl(StockDetailService dataService) {
		this.dataService = dataService;
	}

	
	@Override
	public int calRisk(String id,String start,String end) {
		
		
		Iterator<StockInformVO> voList = dataService.getDetails(id,start,end);
		ArrayList<Double> datas = new ArrayList<>();
	    
		int counter = 0;
		
		while(voList.hasNext()){
			StockInformVO vo = voList.next();
			datas.add(Double.valueOf(vo.getClose()));
		}
		
		double standard = datas.get(datas.size()-1);
		
		for(int i=0;i<datas.size();i++){
			double data = datas.get(i);
			if(data<standard){
				counter++;
			}			
		}
		
		double riskRate = (double)counter/(double)datas.size();
		
		int result = 5 - (int)(riskRate*5);
		
		if(result == 0){
			result ++;
		}
		
		
		return result;
	}

	@Override
	public int calBenfit(String id,String start,String end) {
		
		Iterator<StockInformVO> voList = dataService.getDetails(id,start,end);
		ArrayList<Double> datas = new ArrayList<>();
	    
		while(voList.hasNext()){
			StockInformVO vo = voList.next();
			datas.add(Double.valueOf(vo.getClose()));
		}
		
		double num1 = datas.get(0);
		double num2 = datas.get(datas.size()-1);
		
		double rate = (num2 - num1)/num1;
		
		int result = 3;
		
		if(rate>=0){
			if(rate<=0.1){
				result++;
			}else if(rate>0.1){
				result+=2;
			}
		}else if(rate>= -0.1){
			result --;
		}else{
			result -= 2;
		}
		
		return result;
	}

	@Override
	public int calMarket(String id,String start,String end) {

		Iterator<StockInformVO> voList = dataService.getDetails(id,start,end);
		ArrayList<Double> datas = new ArrayList<>();
	    
		while(voList.hasNext()){
			StockInformVO vo = voList.next();
			datas.add(Double.valueOf(vo.getTurnover()));
		}
		
		double sum = 0;
		
		for(int i=0;i<datas.size();i++){
			sum += datas.get(i);
		}
		
		if(datas.size()==0){
			return -1;
		}
		
		double average = sum/datas.size();
		
		int result = 0;
		
		if(average>5.8){
			result = 5;
		}else if(average > 3.5){
			result = 4;
		}else if(average > 1.8){
			result = 3;
		}else if(average > 1.0){
			result = 2;
		}else {
			result = 1;
		}
		
		return result;
	}

	@Override
	public int calProspect(String id,String start,String end) {
		
		Iterator<StockInformVO> voList = dataService.getDetails(id,start,end);
		ArrayList<Double> datas = new ArrayList<>();
	    
		while(voList.hasNext()){
			StockInformVO vo = voList.next();
			datas.add(Double.valueOf(vo.getPe()));
		}
		
		double sum = 0;
		
		for(int i=0;i<datas.size();i++){
			sum += datas.get(i);
		}
		
		if(datas.size()==0){
			return -1;
		}
		
		double average = sum/datas.size();
		
		int result = 0;
		
		if(average > 60){
			result = 2;
		}else if(average > 40){
			result = 3;
		}else if(average > 14){
			result = 4;
		}else if(average > 0){
			result = 5;
		}else {
			result = 1;
		}
		
		
		return result;
	}


	@Override
	public int calConsistency(String id,String start,String end) {
		
		Iterator<StockInformVO> voList = dataService.getDetails(id,start,end);
		ArrayList<Double> datas = new ArrayList<>();
	    ArrayList<Double> maxs = new ArrayList<>();
		ArrayList<Double> mins = new ArrayList<>();
		
		while(voList.hasNext()){
			
			StockInformVO vo = voList.next();
			datas.add(Double.valueOf(vo.getPe()));
			
			if(datas.size() == 10){
				//当满了10个之后，计算并清空
				double max = datas.get(0);
				double min = datas.get(0);
				for(int i =0 ;i< datas.size();i++){
					double temp = datas.get(i);
					if(temp>max){
						max= temp;
					}
					if(temp<min){
						min = temp;
					}
				}
				
				maxs.add(max);
				mins.add(min);
				
				datas.clear();
			}	
		}
		
		if(datas.size()!=0){
			double max = datas.get(0);
			double min = datas.get(0);
			for(int i =0 ;i< datas.size();i++){
				double temp = datas.get(i);
				if(temp>max){
					max= temp;
				}
				if(temp<min){
					min = temp;
				}
			}
			
			maxs.add(max);
			mins.add(min);
			
			datas.clear();
		}
		
		//使用公式计算
		//results储存波动率计算结果
		ArrayList<Double> results = new ArrayList<>();
		for(int i =0;i < mins.size();i++){
			if(mins.get(i)==0){
				return -1;
			}
			double result = (maxs.get(i) - mins.get(i))/mins.get(i);
			results.add(result);
		}
		
		//计算几何平均数
		double ji = 1;
		for(int i =0;i< results.size();i++){
			ji*=results.get(i);
		}
		
		double answer = Math.pow(ji, 1.0/(double)results.size());
		int score = 0;
		
		//下面把结果转化为5分制
		
		//波动率低于5%为5分，5% - 7%为4分，7%-10%为3分，10%-13%为2分，13%以上为1分
		if(answer>0.13){
			score = 1;
		}else if(answer>0.10){
			score = 2;
		}else if(answer>0.07){
			score = 3;
		}else if(answer>0.05){
			score = 4;
		}else {
			score = 5;
		}
		
		
		return score;
	}

}
