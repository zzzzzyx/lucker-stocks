package cn.edu.nju.luckers.calculate_center.businesslogic.service;

import cn.edu.nju.luckers.calculate_center.global.exception.HaltException;
import cn.edu.nju.luckers.calculate_center.vo.StockScoreVO;

/**
 * 该接口提供对股票得分的计算
 * 提供的数据包括：
 * 风险性、收益性、市场性、前景性
 * 所有的结果都使用5分制并且都是整数
 * @author NjuMzc
 *
 */
public interface StockScoreService {
	
	/**
	 * 返回的VO中可以用key关键字来获得得分，对应关系如下：
	 * 风险性：risk
	 * 市场性：market
	 * 前景性：prospect
	 * 盈利性：benefit
	 * 稳定性：consistency
	 * @param id
	 * @return
	 * @throws HaltException 
	 */
	public StockScoreVO getScore(String id) throws HaltException;

}
