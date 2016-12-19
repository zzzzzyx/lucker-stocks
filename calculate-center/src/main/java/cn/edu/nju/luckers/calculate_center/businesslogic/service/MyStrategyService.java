package cn.edu.nju.luckers.calculate_center.businesslogic.service;

import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;
import net.sf.json.JSONArray;

public interface MyStrategyService {
	
	/*
	 * @param:Strartegy
	 * @return:boolean
	 * @goal:验证制定的新策略是否合理
	 */
	public boolean vertify(Strategy strategy);
	
	/*
	 * @param:策略，开始时间，结束时间
	 * @return:策略收益
	 * @goal:将策略应用在数据上，获得收益值
	 */
	public JSONArray applyOnHistory(Strategy strategy ) ;
	 
}
