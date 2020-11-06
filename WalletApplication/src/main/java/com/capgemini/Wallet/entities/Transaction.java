package com.capgemini.Wallet.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table
public class Transaction implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String statement;
	private double amount;
	private Date timeOfTransaction;
	private long cardNumber;
	private String transactionType;
	
	@ManyToOne
	@JoinColumn(name="mobile")
	private UserInfo userInfo;
	
	
	public long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public Date getTimeOfTransaction() {
		return timeOfTransaction;
	}
	public void setTimeOfTransaction(Date timeOfTransaction) {
		this.timeOfTransaction = timeOfTransaction;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Transaction(String statement, Date timeOfTransaction, String transactionType,double amount) {
		super();
		this.statement = statement;
		this.timeOfTransaction = timeOfTransaction;
		this.transactionType = transactionType;
		this.amount = amount;
	}
	public Transaction() {
		super();
	}
	
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", statement=" + statement + ", timeOfTransaction=" + timeOfTransaction
				+ ", transactionType=" + transactionType + ", userInfo=" + userInfo + "]";
	}
	
	
}
