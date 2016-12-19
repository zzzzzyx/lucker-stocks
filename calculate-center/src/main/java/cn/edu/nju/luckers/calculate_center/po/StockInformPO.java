package cn.edu.nju.luckers.calculate_center.po;

import java.util.ArrayList;

/**
 * 该类是一只股票多天的信息的类
 * @author NjuMzc
 *
 */
public class StockInformPO {
	String id;
	ArrayList<StockOneDayPO> inform;
	
	public StockInformPO(String id){
		this.id = id;
		this.inform = new ArrayList<StockOneDayPO>();
	}
	
	public void addOneDay(StockOneDayPO oneDay){
		inform.add(oneDay);
	}
	
	public ArrayList<StockOneDayPO> getInform(){
		return inform;
	}

}
