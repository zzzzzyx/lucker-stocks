package cn.edu.nju.luckers.database_server.main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import cn.edu.nju.luckers.database_getter_interface.service.favorites.FavoriteService;
import cn.edu.nju.luckers.database_getter_interface.service.login.RegisterAndLoginService;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.StrategyService;
import cn.edu.nju.luckers.database_server.impl.favorites.FavoriteController;
import cn.edu.nju.luckers.database_server.impl.login.LoginController;
import cn.edu.nju.luckers.database_server.impl.strategy.StrategyController;

public class RMIBinding {

	private static final String LocalOuterIP = "115.159.126.212";
	private static final int port = 2014;
	private static final String prefix = "rmi://localhost:" + port + "/";
	
	public static void bindAll() throws RemoteException, MalformedURLException, InstantiationException, IllegalAccessException{
		System.setProperty("java.rmi.server.hostname",LocalOuterIP);
		
		LocateRegistry.createRegistry(port);

		Naming.rebind(prefix + RegisterAndLoginService.class.getSimpleName(), LoginController.class.newInstance());
		Naming.rebind(prefix + FavoriteService.class.getSimpleName(), FavoriteController.class.newInstance());
		Naming.rebind(prefix + StrategyService.class.getSimpleName(), StrategyController.class.newInstance());
	}
	
}
