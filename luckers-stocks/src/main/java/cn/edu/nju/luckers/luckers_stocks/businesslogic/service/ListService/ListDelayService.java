package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.ListService;

import java.io.IOException;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

/**
 * @description 提供股票列表展示服务的接口
 * @author Mzc
 * @date 2016/3/4
 */

/**
 * attention！！！
 * 这个接口已被废弃，不再保证其运行质量，请勿调用
 * @author Mzc
 *
 */
public interface ListDelayService {

	/**
	 * 
	 * @param exchanger 营业厅的代号，可以输入“sh”,"sz",null
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public Iterator<StockInformVO> getItems(String exchanger,int num);
	
	/**
	 * 获得当天的股票列表
	 * @return 一个包含所有股票代号的迭代器
	 */
	public Iterator<String> getIdList(String exchanger);
	
}
