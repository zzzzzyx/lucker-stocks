package cn.edu.nju.luckers.database_server.po;

public class User {

	private Long id;
	
	private String username;
	
	private String email;
	
	private String encryptedPassword;
	
	public User(String username, String email, String encryptedPassword) {
		super();
		this.username = username;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
	}

	public Long getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public User(){}
}
