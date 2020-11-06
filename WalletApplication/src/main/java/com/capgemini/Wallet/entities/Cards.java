package com.capgemini.Wallet.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Cards implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	private long cardNumber;
	private String holderName;
	private int expMonth;
	private int expYear;
	private int cvv;
	
	@ManyToOne
	@JoinColumn(name="mobile")
	private UserInfo userInfo;
	
	
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	public int getExpMonth() {
		return expMonth;
	}
	public void setExpMonth(int expMonth) {
		this.expMonth = expMonth;
	}
	public int getExpYear() {
		return expYear;
	}
	public void setExpYear(int expYear) {
		this.expYear = expYear;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public Cards() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cards(long cardNumber, String holderName, int expMonth, int expYear, int cvv, UserInfo userInfo) {
		super();
		this.cardNumber = cardNumber;
		this.holderName = holderName;
		this.expMonth = expMonth;
		this.expYear = expYear;
		this.cvv = cvv;
		this.userInfo = userInfo;
	}
	

}
