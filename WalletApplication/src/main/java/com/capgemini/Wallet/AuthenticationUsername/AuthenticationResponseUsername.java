package com.capgemini.Wallet.AuthenticationUsername;

public class AuthenticationResponseUsername {

	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public AuthenticationResponseUsername(String jwt) {
		super();
		this.jwt = jwt;
	}

	public AuthenticationResponseUsername() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}