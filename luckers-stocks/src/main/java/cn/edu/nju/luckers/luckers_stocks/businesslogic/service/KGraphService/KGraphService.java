package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.KGraphService;

import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.vo.KGraphVO;

/**
 * 提供K线图相关功能的接口
 * @author NjuMzc
 *
 */
public interface KGraphService {

	/**
	 * 获得日K的方法
	 * 该方法暂时没有完成，请勿调用
	 * @param id
	 * @param start
	 * @param end
	 * @return
	 */
	public Iterator<KGraphVO> getDayK(String id,String start,String end);
	
	/**
	 * 获得周K的方法
	 * 好吧，不知道为什么，这个方法消耗的时间非常非常可怕，请耐心等待，，，最好一次不超过10个
	 * @param id
	 * @param number
	 * @return
	 */
	public Iterator<KGraphVO> getWeekK(String id,int number);
	
	/**
	 * 获得月K的方法
	 * 这个方法的消耗可能更加可怕，特别是在使用到2015年或者更之前的数据的时候，建议不要超过6
	 * @param id
	 * @param number
	 * @return
	 */
	public Iterator<KGraphVO> getMonthK(String id,int number);
	
}
