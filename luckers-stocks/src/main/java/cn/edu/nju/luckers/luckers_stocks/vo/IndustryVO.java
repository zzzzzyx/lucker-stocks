package cn.edu.nju.luckers.luckers_stocks.vo;

import java.util.ArrayList;

/**
 * 行业信息的vo对象
 * @author mzc19
 *
 */
public class IndustryVO {
	
	private String name;//行业的中文名
	
	private ArrayList<String> stockList;//该行业下所具有的股票
	
	public IndustryVO(String name){
		this.name = name;
		this.stockList = new ArrayList<>();
	}
	
	
	//以下是对外接口
	
	/**
	 * 获得该行业的名称
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 向该行业中添加股票
	 * 注意，本方法不检查添加的是否正确，只检查是否重复
	 * @param id
	 * @return
	 */
	public boolean addStock(String id){
		if(stockList.contains(id)){
			return false;
		}else{
			stockList.add(id);
			return true;
		}
	}
	
	/**
	 * 获得该行业下所有的股票的代号列表
	 * @return
	 */
	public ArrayList<String> getList(){
		return stockList;
	}

}
