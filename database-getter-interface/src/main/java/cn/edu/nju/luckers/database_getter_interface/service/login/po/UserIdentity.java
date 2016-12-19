package cn.edu.nju.luckers.database_getter_interface.service.login.po;

import java.io.Serializable;

import cn.edu.nju.luckers.database_getter_interface.util.Encrypter;

public class UserIdentity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 477026749228669193L;

	/**
	 * 账户名或邮箱都可以
	 */
	private String userName;
	
	/**
	 * 登录密码，无须手动加密，构造方法和setter会自动进行加密
	 */
	private String password;

	public UserIdentity(String userName, String password) {
		super();
		this.userName = userName;
		setPassword(password);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = Encrypter.encrypt(password);
	}
}
