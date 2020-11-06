package com.capgemini.Wallet.AuthenticationUsername;

public class AuthenticationRequestUsername {

	private String username;
	private String password;
	private String mobile;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AuthenticationRequestUsername(String username, String password,String mobile) {
		super();
		this.username = username;
		this.mobile=mobile;
		this.password = password;
	}
	public AuthenticationRequestUsername() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
