package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.DetailService;

import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

/**
 * @description 提供查看股票详细信息服务的接口
 * @author Mzc
 * @date 2016/3/4
 */
public interface StockDetailService {
	
	/**
	 * 通过股票Id和起讫日期查询单股详细信息
	 * @param Id 
	 * @param start 开始日期，要求格式为xxxx-xx-xx
	 * @param end 结束日期，要求格式为xxxx-xx-xx
	 * @return 返回股票详细信息数据，内容参考StockInformVO
	 * @exception 1.如果Id不存在，则返回null
	 *            2.如果网络断开，则所有均返回空字符
	 */
     public Iterator<StockInformVO> getDetails(String Id,String start,String end);
     
     /**
      * 仅返回最近一个月内该Id股票的数据信息
      * @param Id
      * @return 返回最近30天该股票的详细信息
      * @exception 如果Id不存在，则返回null
      */
     public Iterator<StockInformVO> getDetails(String Id);
     
     //个人建议：通过日期进行查询的时候应该有默认起讫日期
     
     /**
      * 提供查询某一股票指定日期的数据
      * @param id 股票代号
      * @param date 查询日期
      * @return 当天的数据
      * @exception 股票编号或者日期格式错误(包括当天不是交易日的情况)均会返回null
      */
     public StockInformVO getOneDay(String id,String date);
}
