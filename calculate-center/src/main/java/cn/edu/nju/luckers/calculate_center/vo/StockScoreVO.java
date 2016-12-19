package cn.edu.nju.luckers.calculate_center.vo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 股票得分类
 * 目前包含以下分数项目：
 * 风险性、收益性、市场性、前景性、投资价值
 * @author NjuMzc
 *
 */
public class StockScoreVO {
	
	private Map<String,String> scores;
	
	public StockScoreVO(){
		scores = new HashMap<String, String>();
	}
	
	/**
	 * 设置得分项目
	 * @param key
	 * @param score
	 * @return 
	 */
	public void setScore(String key,String score){
		scores.put(key, score);
	}

	/**
	 * 如果key错误，则返回null
	 * @param key
	 * @return
	 */
	public String getScore(String key){
		String score = scores.get(key);
		if(scores.get(key) == null){
			return "0";
		}
		return score;
	}
	
	public String getSum(){
		Iterator<Entry<String, String>> it = scores.entrySet().iterator();
		double sum = 0;
		while(it.hasNext()){
			String value = it.next().getValue();
			double d = Double.valueOf(value);
			
			sum += d;
		}
		
		return String.valueOf(sum);
	}
}
