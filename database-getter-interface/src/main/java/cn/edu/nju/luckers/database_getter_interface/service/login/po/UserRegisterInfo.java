package cn.edu.nju.luckers.database_getter_interface.service.login.po;

import java.io.Serializable;

import cn.edu.nju.luckers.database_getter_interface.util.Encrypter;

public class UserRegisterInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7534183835341010155L;

	/**
	 * 用户名（必须符合标准名称规范即字母数字下划线的组合，以免出错），无论如何不能包含@，数据库端利用@来检测是邮箱还是用户名
	 */
	private String userName;
	
	/**
	 * 密码，无须手动加密，构造方法和setter会自动进行加密
	 */
	private String password;
	
	/**
	 * email(一定要提前验证里面有没有@字符）
	 */
	private String email;

	public UserRegisterInfo(String userName, String password, String email) {
		super();
		this.userName = userName;
		setPassword(password);
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
