package com.capgemini.Wallet.AuthenticationAadhar;

public class AuthenticationResponseAadhar {

	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public AuthenticationResponseAadhar(String jwt) {
		super();
		this.jwt = jwt;
	}

	public AuthenticationResponseAadhar() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
