package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService;

import cn.edu.nju.luckers.luckers_stocks.vo.MarketDetailVO;

/**
 * @description 获得大盘详细信息接口
 * @author Mzc
 * @date 2016/3/4
 */
public interface MarketDetailService {
	/**
	 * 通过起讫时间获得大盘详细信息的方法
	 * @param id 大盘指数的id
	 * @param start 起时间
	 * @param end 终时间
	 * @return 大盘信息VO对象
	 * @exception 如果id输入错误，返回null
	 */
     public MarketDetailVO getDetails(String id,String start,String end);
}
