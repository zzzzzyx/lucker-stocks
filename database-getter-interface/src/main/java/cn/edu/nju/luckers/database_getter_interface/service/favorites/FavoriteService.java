package cn.edu.nju.luckers.database_getter_interface.service.favorites;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;

public interface FavoriteService extends Remote {

	/**
	 * 获取用户的自选股列表
	 * @param user 用户的信息，包括用户名和密码必须匹配，（如果账号和密码不匹配，仍然正常检索（出于现阶段效率的考虑））
	 * @return 返回用户的自选股列表
	 */
	public ArrayList<String> getUserFavorites(UserIdentity user) throws RemoteException;
	
	/**
	 * 在用户的自选股列表中删除一项
	 * @param user 用户的信息，包括用户名和密码必须匹配，（如果账号和密码不匹配，仍然正常检索（出于现阶段效率的考虑））
	 * @param stockCode 需要删除的股票代号
	 * @return 删除成功即返回true，否则返回false，可能的失败原因可能包括重复添加，股票代号不存在等
	 */
	public boolean deleteStock(UserIdentity user, String stockCode) throws RemoteException;
	
	/**
	 * 在用户的自选股列表中添加一项
	 * @param user 用户的信息，包括用户名和密码必须匹配，（如果账号和密码不匹配，仍然正常检索（出于现阶段效率的考虑））
	 * @param stockCode 需要添加的股票代号
	 * @return 删除成功即返回true，否则返回false，可能的失败原因可能包括删除了不在列表中的股票等
	 */
	public boolean addStock(UserIdentity user, String stockCode) throws RemoteException;
	
	/**
	 * 获取所有股票的代码列表,结果为空则返回null
	 * @return 
	 */
	public List<String> getAllStocks() throws RemoteException;
}
