package cn.edu.nju.luckers.luckers_stocks.businesslogic.impl.KGraphServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService.StockDetailService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.KGraphService.KGraphService;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.DataKey;
import cn.edu.nju.luckers.luckers_stocks.businesslogic.service.LogicServer.MyCalendar;
import cn.edu.nju.luckers.luckers_stocks.vo.KGraphVO;
import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

public class KGraphServiceImpl implements KGraphService {
	StockDetailService stockGetter ;
	
	public KGraphServiceImpl(StockDetailService stockGetter) {
		this.stockGetter = stockGetter;
	}

	@Override
	public Iterator<KGraphVO> getDayK(String id, String start, String end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<KGraphVO> getWeekK(String id, int number) {
		if(number<=0){
			return null;
		}
		
		int counter =0;
		
		String Fridate = MyCalendar.getNearestFri();//最近周五日期
		String Mondate = MyCalendar.getDayByNum(Fridate, 4);//当周周一日期
		Stack<KGraphVO> voStack = new Stack<>();
		ArrayList<KGraphVO> resultList = new ArrayList<>();
		
		while(counter!=number){
            //获得一周的数据，多数情况下是五天的
			//如果当周在工作日期间也有股票停盘，则只计算开盘的天数
			
			Iterator<StockInformVO> voList = stockGetter.getDetails(id, Mondate, MyCalendar.getTomorrow(Fridate));
            ArrayList<StockInformVO> voL = new ArrayList<>();
            
            if(voList == null){
            	//说明这一周都没有数据
            	//直接换到下一周
            	Fridate = MyCalendar.getDayByNum(Fridate, 7);
    			Mondate = MyCalendar.getDayByNum(Mondate, 7);
            	continue;
            }
			
			while(voList.hasNext()){
				StockInformVO vo = voList.next();
				voL.add(vo);
			}
			Map<String,Double> datas = new HashMap<>();
			
			double open =0;
			double close = 0;
			double high = 0;
			double low = 0;
			double volume =0;
			double average_5 = 0;
			double average_10 = 0;
			double average_20 = 0;
			
			if(voL.size()==0){
		
			}else{
				open = Double.valueOf(voL.get(0).getItem(DataKey.open));
				close = Double.valueOf(voL.get(voL.size()-1).getItem(DataKey.close));
				
				high = Double.valueOf(voL.get(0).getItem(DataKey.close));
				low = high;
				for(int i=0;i<voL.size();i++){
					double temp =Double.valueOf(voL.get(i).getItem(DataKey.close));
					if(high< temp){
						high = temp;
					}
					if(low > temp){
						low = temp;
					}
					volume += Double.valueOf(voL.get(i).getItem(DataKey.volume));
				}
				
				int count = 0;
				String tempDate = Fridate;
				String midDate = Fridate;
				double sum =0;
				while(count < 20){
					
					StockInformVO oneDay = stockGetter.getOneDay(id, tempDate);
		
					
					if(oneDay != null){
						sum += Double.valueOf(oneDay.getItem(DataKey.close));
						count ++;
						tempDate = MyCalendar.getDayByNum(tempDate, 7);
					}else{
						//尝试计算最近天数
						int j = 1;
						while(oneDay==null){
							midDate = MyCalendar.getDayByNum(midDate, j);
							oneDay = stockGetter.getOneDay(id,midDate);
							if(oneDay!=null){
								sum += Double.valueOf(oneDay.getItem(DataKey.close));
								count ++;
								tempDate = MyCalendar.getDayByNum(tempDate, 7);
								break;
							}else{
								j++;
								continue;
							}
						}
					
					}
				
					
					if(count==5){
						average_5 = sum/5;
						
					}else if(count == 10){
						average_10 = sum/10;
						
					}else if(count == 20){
						average_20 = sum/20;
						
					}
				}
				//算好了一周的所有数据了
				datas.put("open", open);
				datas.put("close", close);
				datas.put("volume", volume);
				datas.put("high", high);
				datas.put("low", low);
				datas.put("average_5", average_5);
				datas.put("average_10", average_10);
				datas.put("average_20", average_20);
				KGraphVO vo = new KGraphVO(id, Fridate, datas);
				voStack.push(vo);
			}
			counter ++;
			Fridate = MyCalendar.getDayByNum(Fridate, 7);
			Mondate = MyCalendar.getDayByNum(Mondate, 7);
		}
		
		while(!voStack.isEmpty()){
			KGraphVO vo =voStack.pop();
			resultList.add(vo);
		}
				
		return resultList.iterator();
	}

	@Override
	public Iterator<KGraphVO> getMonthK(String id, int number) {
		
		if(number<=0){
			return null;
		}
		
		int counter =0;
		
		String Endate = MyCalendar.getLastMonthEnd(MyCalendar.getToday());//上个月月末
		String StartDate = MyCalendar.getMonthStart(Endate);//当月月初
		Stack<KGraphVO> voStack = new Stack<>();
		ArrayList<KGraphVO> resultList = new ArrayList<>();
		
		while(counter!=number){
            //获得一个月的数据
			//如果当月在工作日期间也有股票停盘，则只计算开盘的天数
			Iterator<StockInformVO> voList = stockGetter.getDetails(id, StartDate, MyCalendar.getTomorrow(Endate));
            ArrayList<StockInformVO> voL = new ArrayList<>();
			
			while(voList.hasNext()){
				StockInformVO vo = voList.next();
				voL.add(vo);
			}
			Map<String,Double> datas = new HashMap<>();
			
			double open =0;
			double close = 0;
			double high = 0;
			double low = 0;
			double volume =0;
			double average_5 = 0;
			double average_10 = 0;
			double average_20 = 0;
			
			if(voL.size()==0){
				//先不考虑这个吧
			}else{
				open = Double.valueOf(voL.get(0).getItem(DataKey.open));
				close = Double.valueOf(voL.get(voL.size()-1).getItem(DataKey.close));
				
				high = Double.valueOf(voL.get(0).getItem(DataKey.close));
				low = high;
				for(int i=0;i<voL.size();i++){
					double temp =Double.valueOf(voL.get(i).getItem(DataKey.close));
					if(high< temp){
						high = temp;
					}
					if(low > temp){
						low = temp;
					}
					volume += Double.valueOf(voL.get(i).getItem(DataKey.volume));
				}
				
				int count = 0;
				String tempDate = Endate;
				double sum =0;
				while(count < 20){
					StockInformVO oneDay = stockGetter.getOneDay(id, tempDate);
					if(oneDay != null){
						sum += Double.valueOf(oneDay.getItem(DataKey.close));
						count ++;
						tempDate = MyCalendar.getLastMonthEnd(tempDate);
					}else{
						tempDate = MyCalendar.getDayByNum(tempDate, 1);
						continue;
					}
					if(count==5){
						average_5 = sum/5;
					}else if(count == 10){
						average_10 = sum/10;
					}else if(count == 20){
						average_20 = sum/20;
					}
				}
				//算好了一月的所有数据了
				datas.put("open", open);
				datas.put("close", close);
				datas.put("volume", volume);
				datas.put("high", high);
				datas.put("low", low);
				datas.put("average_5", average_5);
				datas.put("average_10", average_10);
				datas.put("average_20", average_20);
				KGraphVO vo = new KGraphVO(id, Endate, datas);
				voStack.push(vo);
			}
			counter ++;
			Endate = MyCalendar.getLastMonthEnd(Endate);
			StartDate= MyCalendar.getMonthStart(Endate);
		}
		
		while(!voStack.isEmpty()){
			KGraphVO vo =voStack.pop();
			resultList.add(vo);
		}
				
		return resultList.iterator();
	}

}
