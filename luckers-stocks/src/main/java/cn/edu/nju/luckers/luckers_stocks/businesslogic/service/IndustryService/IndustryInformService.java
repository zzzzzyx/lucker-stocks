package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.IndustryService;

import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.vo.IndustryScoreVO;
import cn.edu.nju.luckers.luckers_stocks.vo.IndustryVO;

/**
 * @description 该接口提供获得行业信息的功能
 * @author mzc
 *
 */
public interface IndustryInformService {

	/**
	 * 获得当前收藏夹中股票所能划分出来的所有行业
	 * @return
	 */
	public Iterator<IndustryVO> getIndustryList();
	
	/**
	 * 通过股票id获得其所属行业
	 * 如果api查不到这个股票就返回null
	 * @param id
	 * @return
	 */
	public String getIndustryByStock(String id);
	
	/**
	 * 获得指定时间段内的行业得分情况
	 * @param name 行业中文名
	 * @param start
	 * @param end
	 * @return 得分的vo对象
	 */
	public IndustryScoreVO getIndustryScore(String name,String start,String end);
}
