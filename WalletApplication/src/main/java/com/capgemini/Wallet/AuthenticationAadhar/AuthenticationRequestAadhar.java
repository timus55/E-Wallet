package com.capgemini.Wallet.AuthenticationAadhar;

public class AuthenticationRequestAadhar {

	private String aadhar;
	private String password;
	
	
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AuthenticationRequestAadhar(String aadhar, String password) {
		super();
		this.aadhar = aadhar;
		this.password = password;
	}
	public AuthenticationRequestAadhar() {
		super();
		// TODO Auto-generated constructor stub
	}
}

