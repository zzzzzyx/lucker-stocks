package cn.edu.nju.luckers.calculate_center.businesslogic.service;
/**
 * 股票最基础的功能实现
 * 包括：
 * 根据ID获得股票名字
 * 指定Id和日期获得股票数据
 * @author NjuMzc
 *
 */
import java.util.ArrayList;

import cn.edu.nju.luckers.calculate_center.vo.StockImVO;
import cn.edu.nju.luckers.calculate_center.vo.StockInformVO;

public interface StockInformService {
	/**
	 * 根据输入的id返回股票的名字
	 * 如果id错误，返回null
	 * @param id
	 * @return
	 */
	public String getName(String id);
	
	/**
	 * 根据id与起始和结束日期获得股票的数据
	 * Attention!!!!!!!!!!!!!!!
	 * 获得的数据包括开始日期，但不包括结束日期
	 * 如果某日期数据不存在，则该日期不会加在链表中
	 * 按日期从前到后顺序存在链表中
	 * @param id
	 * @param start
	 * @param end
	 * @return
	 */
	public ArrayList<StockInformVO> getInform(String id,String start,String end);
	
	/**
	 * 指定某日的股票数据
	 * 如果输入错误或者当天没有数据都会返回null
	 * 请尽量少的调用这个方法
	 * @param id
	 * @param date
	 * @return
	 */
	public StockInformVO getInform(String id,String date);

	
	/**
	 * 根据输入的股票编号返回股票所在的行业名字
	 * @param id
	 * @return
	 */
	public String getIndustry(String id);
	
	/**
	 * 获得股票实时数据的接口
	 * 包含：时间点（格式如0930，九点半，24小时制），价格，均值，成交量（单位：手）
	 * @param id
	 * @return
	 */
	public ArrayList<StockImVO> getImmediate(String id);
}
