package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.MarketService;


import cn.edu.nju.luckers.luckers_stocks.vo.MarketInformVO;

/**
 * @description 提供大盘信息查看功能的接口
 * @author Mzc
 * @date 2016/3/4
 **/
public interface MarketInformService {

	/**
	 * 获得大盘概要信息的方法
	 * @param id可以输入“hs300”，之后应该会有更多的id可提供
	 * @return 大盘最近一天的数据
	 * @exception 如果id输入错误，返回null
	 */
	public MarketInformVO getItems(String id);
}
