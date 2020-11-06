package com.capgemini.Wallet.AuthenticationNumber;


/*
 * Request Bean for of mobile number and password
 * Used for generation of JWT
 */
public class AuthenticationRequest {

	private String mobile;
	private String password;
	
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AuthenticationRequest(String mobile, String password) {
		super();
		this.mobile = mobile;
		this.password = password;
	}
	public AuthenticationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
}
