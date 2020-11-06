package com.capgemini.Wallet.entities;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

/*
 * Table and Entity annotation will create the respective table in database.
 * we can mentioned the name of table if need different name from class name.
 */

@Entity
@Table(name="Details")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class UserInfo implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/*
	 * Id annotation will describe the primary key in the respective table.
	 */
	@Id
	@NotNull
	private String mobile;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String role;
	private double balance;
	private String aadhar;
	private String securityQ;
	private String securityA;
	private String pin;
	
	
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}

	@JsonBackReference(value="Transaction")
	@OneToMany(mappedBy="userInfo",cascade=CascadeType.ALL)
	private List<Transaction> transactions=new ArrayList<>();
	
	 @JsonBackReference(value="Cards")
	 @JsonIgnoreProperties
	@OneToMany(mappedBy="userInfo",cascade=CascadeType.ALL)
	private List<Cards> cards=new ArrayList<>();
	
	
	
	
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public List<Cards> getCards() {
		return cards;
	}
	public void setCards(List<Cards> cards) {
		this.cards = cards;
	}

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	public String getSecurityQ() {
		return securityQ;
	}
	public void setSecurityQ(String securityQ) {
		this.securityQ = securityQ;
	}
	public String getSecurityA() {
		return securityA;
	}
	public void setSecurityA(String securityA) {
		this.securityA = securityA;
	}
	public UserInfo()
	{
		super();
	}
	
	public UserInfo(String mobile, String firstName, String lastName, String username, String password, String role,
			String pin, double balance, String aadhar, String securityQ, String securityA,
			List<Transaction> transactions, List<Cards> cards) {
		super();
		this.mobile = mobile;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
		this.balance = balance;
		this.aadhar = aadhar;
		this.securityQ = securityQ;
		this.securityA = securityA;
		this.transactions = transactions;
		this.cards = cards;
		this.pin=pin;
	}
	
	public UserInfo(String string, String string2, String string3, String string4, String string5, String string6,
			double i, long l, String string7, String string8) {
	}
	public void addTransaction(Transaction transaction, String type,String statement,double amount) 
	{
		transaction.setUserInfo(this);
		transaction.setTimeOfTransaction((Calendar.getInstance().getTime()));
		transaction.setTransactionType(type);
		transaction.setStatement(statement);
		transaction.setAmount(amount);
		this.getTransactions().add(transaction);
	System.out.println("userinfo");
	}
	
	public void addTransactionCard(Transaction transaction,String type,String statement,double amount,long cardNumber) 
	{
		transaction.setUserInfo(this);
		transaction.setCardNumber(cardNumber);
		transaction.setTimeOfTransaction((Calendar.getInstance().getTime()));
		transaction.setTransactionType(type);
		transaction.setStatement(statement);
		transaction.setAmount(amount);
		this.getTransactions().add(transaction);
	}
	public UserInfo(@NotNull String mobile, String firstName, String lastName, String username, String password,
			String role, double balance, String aadhar, String securityQ, String securityA, String pin) {
		super();
		this.mobile = mobile;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
		this.balance = balance;
		this.aadhar = aadhar;
		this.securityQ = securityQ;
		this.securityA = securityA;
		this.pin = pin;
	}
	
	
}
