package com.capgemini.Wallet.AuthenticationNumber;

/*
 * Response bean. jwt will store in string jwt after generation
 */
public class AuthenticationResponse {

	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public AuthenticationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
