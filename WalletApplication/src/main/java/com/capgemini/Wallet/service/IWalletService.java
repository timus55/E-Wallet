package com.capgemini.Wallet.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.capgemini.Wallet.bean.EditUserDetails;
import com.capgemini.Wallet.entities.*;

/**
 * This is service layer interface with contains declaration of all the functions.
 *
 */
public interface IWalletService {
	
	public String addUser(UserInfo user);
	
//	public String loginByUsername(UserInfo user);
//	
//	public String loginByNumber(UserInfo user);
//	
//	public String loginByAadhar(UserInfo user);
//	
	public double checkBalance(String mobile);
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	public String bankToWallet(String mobile,long cardNumber,double amount);
	
	public String walletToBank(String mobile,long cardNumber,double amount,String pin);
	
	public List<Transaction> viewAddMoneyHistory(String mobile);
	
	public List<Cards> viewCards(String mobile);
	
	public String addCard(Cards card,String mobile);
	
	public String deleteCard(long cardNumber,String mobile);
	
	public String deleteAllCards(String mobile);

	public String changePassword(String mobile, String oldPassword, String newPassword);

	public String forgotPassword(String mobile, String securityQ, String securityA);
	
	public String forgotPin(String mobile, String Password);

	public EditUserDetails getUserDetails(String mobile);

	public String editUser(String mobile, EditUserDetails user);

	public List<UserInfo> getAllUsers(String mobile);

	public boolean deleteuser(String mobile);

	public String transfer(String mobile, String receiver, double amount, String pin);

	public List<Transaction> getTransaction(String mobile);

	public String getRole(String mobile);


	
	
}
