package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService;

import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;
import cn.edu.nju.luckers.luckers_stocks.vo.StockScreenVO;

/**
 * @description 提供用关键字进行筛选功能的接口
 * @author Mzc
 * @date 2016/03/11
 */
public interface DataScreenService {

	/**
	 * 
	 * @param id 股票代号
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param voList 筛选项目的迭代器
	 * @return 包含符合条件的VO的迭代器，VO中的内容有日期和关键字中包含的所有项目
	 * @exception 日期输入格式不对，则抛出DateFormatException
	 */
	public Iterator<StockInformVO> getChosen(String id, String startDate, String endDate,
			ArrayList<StockScreenVO> voList);
}
 