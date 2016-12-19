package cn.edu.nju.luckers.database_getter_interface.service.login;

import java.rmi.Remote;
import java.rmi.RemoteException;

import cn.edu.nju.luckers.database_getter_interface.service.login.po.*;

public interface RegisterAndLoginService extends Remote {

	/**
	 * 注意：数据库端不会验证用户名，密码，邮箱是否符合规范，但会验证用户名和邮箱是否与已有的重复
	 * 如果用户名和邮箱与已有的重复，数据库的插入操作会失败，返回false。然而还是建议先验证是否重复
	 * @param userInfo注册的信息
	 * @return 返回true时注册成功，返回false时注册失败，失败原因可能包括账户或邮箱重复
	 * @throws RemoteException
	 */
	public boolean register(UserRegisterInfo userInfo) throws RemoteException;
	
	/**
	 * @param userInfo 用户的登录信息，包含用户名（或邮箱）和密码
	 * @return 如果用户名和密码匹配，返回true，否则返回false
	 * @throws RemoteException
	 */
	public boolean verify(UserIdentity userInfo) throws RemoteException;
	
	/**
	 * 处理用户的注销，web端可不使用此功能，主要是测试的时候需要这个功能来实现自动化测试
	 * @param userInfo
	 * @return 注销成功返回true，否则返回false，失败原因可能包括账号和密码验证失败等原因
	 * @throws RemoteException
	 */
	public boolean deleteUser(UserIdentity userInfo) throws RemoteException;
	
	/**
	 * @param name 参数为邮箱或用户名，实现端会用是否包含‘@’字符来判断到底是哪个属性
	 * @return 被占用返回true，未被占用返回false
	 * @throws RemoteException
	 */
	public boolean isUsernameOrEmailOccupied(String name) throws RemoteException;
}
