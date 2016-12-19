package cn.edu.nju.luckers.database_getter_interface;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;  

import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.StrategyService;
import cn.edu.nju.luckers.database_getter_interface.service.strategy.po.Strategy;

//@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)  
public class StrategyTest {

	private StrategyService service;
	
	private final String TestUserName1 = "IamATester";
	private final String TestPassword1 = "IamATester2016PSWD";
	private UserIdentity testuser = new UserIdentity(TestUserName1, TestPassword1);
	
	@Before
	public void initial() throws MalformedURLException, RemoteException, NotBoundException{
		service = (StrategyService)RMIService.getInstance(StrategyService.class);
	}	
	
	
	@Test
	public void test001addAndGetStrategy() throws RemoteException{
		
		assertNull(service.getStrategy(testuser));
		
		Strategy s1 = new Strategy("sh600400","2016-5-26", "2.7", "2.7", 9.4, 9.4, 9.4,
				15667, 15667, 9.4, 9.4, 9.4, 9.4, 9.4, 9.4, 9.4, 9.4, 
				152, 137, 112, 138.4, 9.4, 9.4, 9.4, 9.4,"汇丰银行","脑残策略","刘兴");
		assertTrue(service.createStrategy(testuser, s1));
		assertTrue(service.getStrategy(testuser).size() == 1);
		
		Strategy s2 = new Strategy("sh600700","2016-4-27", "2.6", "2.8", 9.4, 9.4, 9.4,
				15667, 15667, 9.4, 9.4, 9.4, 9.4, 9.4, 9.4, 9.4, 9.4, 
				152, 137, 112, 138.4, 9.4, 9.4, 9.4, 9.4,"八股文股票","谁的策略","孟鑫");
		assertTrue(service.createStrategy(testuser, s2));
		List<Strategy> strategyList = service.getStrategy(testuser);
		assertTrue(strategyList.size() == 2);
		
		assertEquals(s1.toString(), strategyList.get(0).toString());
		assertNotEquals(s2.toString(), strategyList.get(0).toString());
		assertEquals(s2.toString(), strategyList.get(1).toString());
	}
	
	@Test
	public void test004getAndDeleteStrategy() throws RemoteException{
		List<Strategy> strategyList = service.getStrategy(testuser);
		assertTrue(strategyList.size() == 2);
		
		assertTrue(service.deleteStrategy(testuser, strategyList.get(1).getId()));
		
		strategyList = service.getStrategy(testuser);
		assertTrue(strategyList.size() == 1);
		
		assertTrue(service.deleteStrategy(testuser, strategyList.get(0).getId()));
		
		strategyList = service.getStrategy(testuser);
		assertNull(strategyList);
	}
	
	@Test
	public void test002getOneStrategy() throws RemoteException{
//		Strategy s = service.getOneStrategy(testuser, 48L);
//		assertNotNull(s.getPb_in_low());
	}
	
//	@Ignore
	@Test
	public void test003getAllStrategy() throws RemoteException{
		List<Strategy> s = service.getAllStrategy();
		for(Strategy sds : s){
			System.out.println(sds.getUserName());
		}
		assertTrue(s.size() > 1);
	}
}
