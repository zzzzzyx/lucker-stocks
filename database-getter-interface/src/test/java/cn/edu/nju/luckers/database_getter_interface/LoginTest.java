package cn.edu.nju.luckers.database_getter_interface;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.*;
import org.junit.runners.MethodSorters;  

import cn.edu.nju.luckers.database_getter_interface.common.RMIService;
import cn.edu.nju.luckers.database_getter_interface.service.login.RegisterAndLoginService;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserIdentity;
import cn.edu.nju.luckers.database_getter_interface.service.login.po.UserRegisterInfo;

//@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)  
public class LoginTest {
	
	private RegisterAndLoginService service;
	
	private final String TestUserName1 = "Test_Name_1";
	private final String TestPassword1 = "qwerty9iO0p";
	private final String TestEmail1 = "9090980808@qq.com";
	private UserRegisterInfo user1register = new UserRegisterInfo(TestUserName1, TestPassword1, TestEmail1);//正常注册
	private UserRegisterInfo user1register_false1 = new UserRegisterInfo(TestUserName1, "dsadada221", "312312434@126.com");//测试注册用户名被占用的异常
	private UserRegisterInfo user1register_false2 = new UserRegisterInfo("muriqi2016", "dsadada221", TestEmail1);//测试注册email被占用的异常
	private UserIdentity user1ID1 = new UserIdentity(TestUserName1, TestPassword1);//测试以用户名正常登陆
	private UserIdentity user1ID_false1 = new UserIdentity(TestUserName1, "QWerty9iO0p");//测试以用户名登录失败
	private UserIdentity user1ID2 = new UserIdentity(TestEmail1, TestPassword1);//测试以邮箱名正常登陆
	private UserIdentity user1ID_false2 = new UserIdentity(TestEmail1, "qwer12qqO0p");//测试以邮箱名登陆失败

	@Before
	public void initial() throws MalformedURLException, RemoteException, NotBoundException{
		service = (RegisterAndLoginService)RMIService.getInstance(RegisterAndLoginService.class);
	}	
	
	@Test
	public void test001NormalRegister() throws RemoteException, InterruptedException{
		
		assertFalse(service.isUsernameOrEmailOccupied(TestUserName1));
		assertFalse(service.isUsernameOrEmailOccupied(TestEmail1));
		assertFalse(service.isUsernameOrEmailOccupied(TestPassword1));
		
		assertTrue(service.register(user1register));
		assertFalse(service.register(user1register_false1));
		assertFalse(service.register(user1register_false2));
		
		assertTrue(service.isUsernameOrEmailOccupied(TestUserName1));
		assertTrue(service.isUsernameOrEmailOccupied(TestEmail1));
		assertFalse(service.isUsernameOrEmailOccupied(TestPassword1));
	}
	
	@Test
	public void test002LoginAndDeleteUser() throws RemoteException, InterruptedException{
		
		//此时是第一次注册，测试登陆
		assertTrue(service.verify(user1ID1));
		assertTrue(service.verify(user1ID2));
		assertFalse(service.verify(user1ID_false1));
		assertFalse(service.verify(user1ID_false2));
		
		//测试第一次删除，以用户名方式删除
		assertFalse(service.deleteUser(user1ID_false1));
		assertFalse(service.deleteUser(user1ID_false2));
		assertTrue(service.deleteUser(user1ID1));
		assertFalse(service.deleteUser(user1ID2));
		assertFalse(service.deleteUser(user1ID_false1));
		assertFalse(service.deleteUser(user1ID_false2));
		
		//再次注册同一个账号，测试登陆后以邮箱方式删除
		assertTrue(service.register(user1register));
		assertTrue(service.verify(user1ID1));
		assertTrue(service.verify(user1ID2));
		assertFalse(service.verify(user1ID_false1));
		assertFalse(service.verify(user1ID_false2));
		assertTrue(service.deleteUser(user1ID2));
		
		//再次测试登陆，全部登陆都理应失败
		assertFalse(service.verify(user1ID1));
		assertFalse(service.verify(user1ID2));
		assertFalse(service.verify(user1ID_false1));
		assertFalse(service.verify(user1ID_false2));
	}
}
