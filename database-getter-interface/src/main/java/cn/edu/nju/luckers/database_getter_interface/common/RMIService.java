package cn.edu.nju.luckers.database_getter_interface.common;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIService {

	public static final String central_Server_IP = "115.159.126.212";
	private static final int port = 2014;
	
	/**
	 * 根据接口的反射获取远程可操作的RMI对象
	 * @param reflectClass 这是所需要的接口的反射，比如可传入RegisterAndLoginService.class
	 * @return 返回RMI对象的实例，需要在远端进行强制转换
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	@SuppressWarnings("rawtypes")
	public static Object getInstance(Class reflectClass) throws MalformedURLException, RemoteException, NotBoundException{
		Object o = Naming.lookup("rmi://" + central_Server_IP + ":" + port + "/" + reflectClass.getSimpleName());
		return o;
	}
}
