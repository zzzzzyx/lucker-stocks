package cn.edu.nju.luckers.database_getter_interface.service.strategy;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;

public interface StrategyService extends Remote {

	/**
	 * @param user 数据库根据这个类，自动获取用户id并标记到策略
	 * @param strategy 策略
	 * @return 生成成功返回true，失败返回false
	 * @throws RemoteException
	 */
	public boolean createStrategy(UserIdentity user, Strategy strategy) throws RemoteException;
	
	/**
	 * @param user 用户信息
	 * @return 用户的策略列表,如果为空，返回null
	 * @throws RemoteException
	 */
	public List<Strategy> getStrategy(UserIdentity user) throws RemoteException;
	
	/**
	 * @param user 用户信息
	 * @return 获取系统所有的策略,如果为空，返回null
	 * @throws RemoteException
	 */
	public List<Strategy> getAllStrategy() throws RemoteException;
	
	/**
	 * @param user 用户信息
	 * @return 用户的策略列表,如果为空，返回null
	 * @throws RemoteException
	 */
	public Strategy getOneStrategy(UserIdentity user, Long strategyid) throws RemoteException;
	
	/**
	 * @param user
	 * @param strategyid 用户的策略id，可使用strategy.getID()获取，即数据库条目的主键
	 * @return 删除成功返回true，否则返回false
	 * @throws RemoteException
	 */
	public boolean deleteStrategy(UserIdentity user, Long strategyid) throws RemoteException;
}
