package cn.edu.nju.luckers.calculate_center.businesslogic.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.nju.luckers.calculate_center.businesslogic.service.StockScoreService;
import cn.edu.nju.luckers.calculate_center.data.DataController;
import cn.edu.nju.luckers.calculate_center.data.network.exception.ConnectionFailure;
import cn.edu.nju.luckers.calculate_center.data.network.service.StockDataGetter;
import cn.edu.nju.luckers.calculate_center.global.MyCalendar;
import cn.edu.nju.luckers.calculate_center.global.exception.HaltException;
import cn.edu.nju.luckers.calculate_center.po.StockInformPO;
import cn.edu.nju.luckers.calculate_center.po.StockOneDayPO;
import cn.edu.nju.luckers.calculate_center.vo.StockScoreVO;

public class StockScoreServer implements StockScoreService {
	
	private StockDataGetter dataService;
	
	public StockScoreServer(){
		this.dataService = DataController.getDataGetter();
	}


	public StockScoreVO getScore(String id) throws HaltException {
		StockInformPO inform = null;
		try {
			inform = dataService.getStockByID(id, MyCalendar.getDayByNum(40)
					, MyCalendar.getToday());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionFailure e) {
			return null;
		}
		
		if(inform == null){
			return null;
		}
		
		StockScoreVO result = new StockScoreVO();
		
		ArrayList<StockOneDayPO> list = inform.getInform();
		
		if(list.size() == 0){
			throw new HaltException();
		}
		
		result.setScore("risk", String.valueOf(calRisk(inform.getInform().iterator())));
		result.setScore("benefit", String.valueOf(calBenefit(inform.getInform().iterator())));
		result.setScore("consistency", String.valueOf(calConsistency(inform.getInform().iterator())));
		result.setScore("market", String.valueOf(calMarket(inform.getInform().iterator())));
		result.setScore("prospect", String.valueOf(calProspect(inform.getInform().iterator())));
		
		
		return result;
	}

	/**
	 * 安全计算
	 * 该属性描述现在对该股票投资收益的可能性
	 * 计算公式:以最近的收盘价为基点，计算最近的40天数据（30天容易出现奇怪的bug）
	 * 在该收盘价以下的数据比例；
	 * 结果越高，风险性越大，评分越低
	 * 最终返回值是x-X*5取整，如果为0则加1
	 * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	private double calRisk(Iterator<StockOneDayPO> list) {
		
		ArrayList<Double> datas = new ArrayList<Double>();
	    
		int counter = 0;
		
		while(list.hasNext()){
			StockOneDayPO po = list.next();
			datas.add(Double.valueOf(po.getDataMap().get("close")));
		}
		 
		
		double standard = datas.get(datas.size()-1);
		
		for(int i=0;i<datas.size();i++){
			double data = datas.get(i);
			if(data<standard){
				counter++;
			}			
		}
		
		double riskRate = (double)counter/(double)datas.size();
		
		double result = 5-riskRate*5;
		
		if(result == 0){
			result ++;
		}
		
		
		return result;
	}


	/**
     * 盈利性计算
     * 该属性描述该该股票一个月内的增长额度
     * 基础分为3，涨幅10%以内加1，10%以上加2，跌则相反，最低分为1
     * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
     */
	private double calBenefit(Iterator<StockOneDayPO> list) {
		
		ArrayList<Double> datas = new ArrayList<Double>();
	    
		while(list.hasNext()){
			StockOneDayPO po = list.next();
			datas.add(Double.valueOf(po.getDataMap().get("close")));
		}
		
		double num1 = datas.get(0);
		double num2 = datas.get(datas.size()-1);
		
		double rate = (num2 - num1)/num1;
		
		double result = 3;
		
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


	/**
	 * 市场性计算
	 * 该属性描述该股票在市场上交易的火热程度
	 * 计算方式为：换手率0-1.0之间为1分，1.0-1.8之间为2分，1.8到3.5为3分
	 *           3.5-5.8之间为4分，5.8以上为5分
	 *  换手率计算取40天内所有数据的平均值
	 * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	private int calMarket(Iterator<StockOneDayPO> list) {

		ArrayList<Double> datas = new ArrayList<Double>();
	    
		while(list.hasNext()){
			StockOneDayPO po = list.next();
			datas.add(Double.valueOf(po.getDataMap().get("turnover")));
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

	
	/**
	 * 投资价值计,即前景性
	 * 
	 * 该述性描述该股票的投资价值，主要依赖市盈率
	 * <0 ：指该公司盈利为负（因盈利为负，计算市盈率没有意义，所以一般软件显示为“—”） 1分
		0-13 ：即价值被低估 5分
		14-20：即正常水平 4分
		21-28：即价值被高估 3分
		28+ ：反映股市出现投机性泡沫  2分
		来自百度百科
	 * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	private int calProspect(Iterator<StockOneDayPO> list) {
		

		ArrayList<Double> datas = new ArrayList<Double>();
	    
		while(list.hasNext()){
			StockOneDayPO po = list.next();
			datas.add(Double.valueOf(po.getDataMap().get("pe_ttm")));
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


	/**
	 * 稳定性计算
	 * 该属性描述股票的波动程度，计算方式为每10天取股价最大值最小值，（Xh-Xl）/Xl 求得波动程度，再取几何平均数
	 * @param id 股票代号
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return
	 */
	private int calConsistency(Iterator<StockOneDayPO> list) {
	
		ArrayList<Double> datas = new ArrayList<Double>();
	    ArrayList<Double> maxs = new ArrayList<Double>();
		ArrayList<Double> mins = new ArrayList<Double>();
		
		while(list.hasNext()){
			
			StockOneDayPO po = list.next();
			datas.add(Double.valueOf(po.getDataMap().get("close")));
			
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
		ArrayList<Double> results = new ArrayList<Double>();
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
