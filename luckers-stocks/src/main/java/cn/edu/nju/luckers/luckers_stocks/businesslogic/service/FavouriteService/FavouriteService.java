package cn.edu.nju.luckers.luckers_stocks.businesslogic.service.FavouriteService;

import java.util.ArrayList;
import java.util.Iterator;

import cn.edu.nju.luckers.luckers_stocks.vo.StockInformVO;

/**
 * @description 收藏夹功能的相关接口
 * @author Mzc
 * @date 2016/3/4
 */
public interface FavouriteService {
    
	/**
	 * 获得本地收藏的所有股票的代码列表
	 * @return
	 */
	public ArrayList<String> getList();
	/**
	 * @return 返回收藏夹中所有的股票的信息
	 */
	public Iterator<StockInformVO> GetContent();
	
	/**
	 * @param Id 目标股票的代号
	 * @return 如果收藏夹中已存在目标股票，返回false；否则，返回true
	 */
	public boolean add(String Id);
	
	/**
	 * @param Id 目标股票的代号
	 * @return 如果收藏夹中不存在目标股票，则返回false；否则，返回true
	 */
	public boolean remove(String Id);
}
