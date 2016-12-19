package cn.edu.nju.luckers.database_getter_interface;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.favorites.FavoriteService;
import cn.edu.nju.luckers.database_getter_interface.service.login.RegisterAndLoginService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;

//@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)  
public class FavoriteTest {
	
	private RegisterAndLoginService loginService;
	private FavoriteService favoriteService;
	private final String TestUserName1 = "IamATester";
	private final String TestPassword1 = "IamATester2016PSWD";
	private UserIdentity testuser = new UserIdentity(TestUserName1, TestPassword1);
	private ArrayList<String> favorites = null;
	private String addStockCode = "sh600482";
	
	@Before
	public void initial() throws MalformedURLException, RemoteException, NotBoundException{
		loginService = (RegisterAndLoginService)RMIService.getInstance(RegisterAndLoginService.class);
		favoriteService = (FavoriteService)RMIService.getInstance(FavoriteService.class);
//		assertTrue(loginService.register(new UserRegisterInfo(TestUserName1, TestPassword1, "hahaha@163.com")));
	}

	@Test
	public void test001GetFavorites() throws RemoteException{
		favorites = favoriteService.getUserFavorites(testuser);
		assertNotNull(favorites);
//		assertNull(favoriteService.getUserFavorites(testuser_false1));
//		assertNull(favoriteService.getUserFavorites(testuser_false2));
		
		assertTrue(favorites.size() > 1);
		for(String stock: favorites){
			assertTrue(stock.length() == 8);
		}
	}
	
	@Test
	public void test002AddFavorites() throws RemoteException, InterruptedException{
		assertTrue(favoriteService.addStock(testuser, addStockCode));
		Thread.sleep(100);
		assertFalse(favoriteService.addStock(testuser, addStockCode));//重复添加
	}
	
	@Test
	public void test003DeleteFavorites() throws RemoteException, InterruptedException{
		assertTrue(favoriteService.deleteStock(testuser, addStockCode));
		Thread.sleep(100);
		assertFalse(favoriteService.deleteStock(testuser, addStockCode));//重复删除
	}
	
	@Test
	public void test004GetAllStocks() throws RemoteException, InterruptedException{
		List<String> list = favoriteService.getAllStocks();
		assertTrue(list.size() > 3);
		for(String code : list){
			assertTrue(code.length() == 8);
			System.out.println("code >> " + code);
		}
	}
}
