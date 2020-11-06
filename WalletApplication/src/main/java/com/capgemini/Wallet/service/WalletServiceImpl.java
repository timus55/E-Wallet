package com.capgemini.Wallet.service;


//Add Comments on prior basic
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.capgemini.Wallet.DAO.*;
import com.capgemini.Wallet.bean.EditUserDetails;
import com.capgemini.Wallet.entities.*;
import com.capgemini.Wallet.response.ErrorMessage;
import com.capgemini.Wallet.utility.GlobalResources;

/**
 * @Service annotation is used in your service layer and annotates classes that
 *          perform service tasks, often you don't use it but in many case you
 *          use this annotation to represent a best practice
 */
@Service
public class WalletServiceImpl implements UserDetailsService, IWalletService {

	/**
	 * Autowires UserDAO object (Dependency Injection)
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * Autowires CardsDAO object (Dependency Injection)
	 */
	@Autowired
	private CardsDAO cardsDAO;

	private Logger logger = GlobalResources.getLogger(WalletServiceImpl.class);

//	for JWT Authentication

	/**
	 * loadUserByUsername() will load the user details on the basic of primary key.
	 * In our case mobile number
	 */
	@Override
	public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
		{
		
		if(mobile.endsWith("@gmail.com"))
		{
			try {
				UserInfo user = userDAO.findByUsername(mobile);

				return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
			} catch (Exception e) {
				logger.error(e.getMessage());
				System.out.println("ser");
				throw e;
			}
		}
			
			
		else if(mobile.length()==12)
		{
		try {
			UserInfo user = userDAO.findByAadhar(mobile);

			return new User(user.getAadhar(), user.getPassword(), new ArrayList<>());
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.out.println("ser");
			throw e;
		}
}
		else
		{

				try {
					UserInfo user = userDAO.findByMobile(mobile);

					return new User(user.getMobile(), user.getPassword(), new ArrayList<>());
				} catch (Exception e) {
					logger.error(e.getMessage());
					System.out.println("ser");
					throw e;
				}

			}
		}
	}


	/**
	 * addUser() used for registering the new user. It will check all the
	 * validations such as mobile number start from 6,7,8,9 and valid user name and
	 * password should follow the perfect patter. It will also check the user
	 * name,mobile number and aadhar card number should be unique. After registering
	 * the user, mobile number will be printed
	 */

	public String addUser(UserInfo user) {

		try {

			if (user.getFirstName().isEmpty() || user.getFirstName().trim().length() == 0)

			{
				return "Name can't be Null..!!";
			}

			else if (!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15}", user.getPassword()))

			{
				return "Please enter valid password..!! It should have one Uppercase and one Lowercase and one special character..!! and should be of minimum 8 characters..!!";
			}

			else if (!Pattern.matches(
					"^[A-Z a-z 0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$",
					user.getUsername()))

			{
				return "Enter Valid Email ID";
			}

			else if (!Pattern.matches("[6-9][0-9]{9}", String.valueOf(user.getMobile())))

			{

				return "Please enter a valid mobile number..!! \n It should start with 6 to 9 and should have 10 digits";

			}

			else if (!Pattern.matches("[0-9]{12}", String.valueOf(user.getAadhar())))

			{
				return ("Adhar Length should be 12 digits only..!!");
			}

			else if (userDAO.existsByUsername(user.getUsername()))

			{
				return "Username Already Exists.Try again with different Username...";
			}

			else if (userDAO.existsByAadhar(user.getAadhar())) {
				return "Duplicate Aadhar card number. Try login/ forgot password.";
			}

			else if (userDAO.existsByMobile(user.getMobile()))

			{
				return "Duplicate mobile number Try login/ forgot password";
			}

			else

			{
				int randomPIN = (int) (Math.random() * 9000) + 1000;
				String val = "" + randomPIN;

				user.setPin(BCrypt.hashpw(val, BCrypt.gensalt(12)));
				user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
				userDAO.save(user);
				logger.info(user.getUsername() + " is registered successfully with Pin " + val);

				return user.getUsername() + " is registered successfully with Pin " + val;
			}

		} catch (Exception e) {

			logger.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * Login the user according to user name and password and should return false if
	 * credential do not match Exception handling is also there
	 */

//	public String loginByUsername(UserInfo user)
//
//	{
//		try {
//
//			UserInfo user1 = userDAO.findByUsername(user.getUsername());
//
//			String mobileNumber = "";
//
//			if (user1.getUsername().equals(user.getUsername())
//					&& BCrypt.checkpw(user.getPassword(), user1.getPassword())) {
//				mobileNumber = user1.getMobile();
//				logger.info("Login by username");
//				return mobileNumber;
//			}
//			return "Failed";
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			return e.getMessage() + " Because you have entered invalid username";
//		}
//	}
//
//	/**
//	 * Login the user according to Mobile Number and password and should return
//	 * false if credential do not match Exception handling is also there
//	 */
//	public String loginByNumber(UserInfo user) {
//		try {
//			UserInfo user1 = userDAO.findByMobile(user.getMobile());
//
//			if (BCrypt.checkpw(user.getPassword(), user1.getPassword())) {
//				String mobileNumber = user1.getMobile();
//				System.out.println(mobileNumber);
//				logger.info("Login by Number");
//				return mobileNumber;
//			}
//
//			return "Failed";
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			return e.getMessage() + " Because you have entered invalid Mobile Number";
//		}
//	}
//
//	/**
//	 * Login the user according to Aadhar card and password and should return false
//	 * if credential do not match Exception handling is also there
//	 */
//	public String loginByAadhar(UserInfo user) {
//		try {
//			UserInfo user1 = userDAO.findByAadhar(user.getAadhar());
//			String mobileNumber = "";
//
//			if (user1.getAadhar() == user.getAadhar() && BCrypt.checkpw(user.getPassword(), user1.getPassword()))
//
//			{
//				mobileNumber = user1.getMobile();
//			}
//
//			logger.info("Login by Aadhar");
//
//			return mobileNumber;
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			return e.getMessage() + " Because you have entered invalid Aadhar Card Number";
//		}
//	}

	/**
	 * User can check the balance by pressing a button
	 */

	public double checkBalance(String mobile) {
		try {
			UserInfo user = userDAO.findByMobile(mobile);
			logger.info("In Check Balance");
			return user.getBalance();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public String changePassword(String mobile, String oldPassword, String newPassword) {
		try {
			UserInfo user = userDAO.findByMobile(mobile);

			if (!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15}", newPassword)) {
				return "Enter Valid password";
			}

			if (user.getMobile().equals(mobile)) {
				if (BCrypt.checkpw(oldPassword, user.getPassword())) {
					System.out.println(user.getPassword());

					String hashNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
					user.setPassword(hashNewPassword);
					userDAO.save(user);
					logger.info("Password changed for " + user.getUsername());
					return "Changed Password successfully";

				}
			}
		} catch (Exception exception) {
			logger.error(exception.getMessage());
			throw exception;
		}
		logger.error("Incorrect current Password");
		return "Incorrect current Password";
	}

	@Override
	public String forgotPassword(String mobile, String securityQ, String securityA) {
		try {
			UserInfo user = userDAO.findByMobile(mobile);
			if (user.getSecurityQ().equals(securityQ) && user.getSecurityA().equals(securityA)) {

				String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

				StringBuilder sb = new StringBuilder(8);

				for (int i = 0; i <= 8; i++) {

					int index = (int) (AlphaNumericString.length() * Math.random());

					// add Character one by one in end of sb
					sb.append(AlphaNumericString.charAt(index));
				}

				String randomPass = sb.toString();
				user.setPassword(BCrypt.hashpw(randomPass, BCrypt.gensalt(12)));
				userDAO.save(user);
				logger.info("Random Password generated");
				return randomPass;
			} else {
				logger.error("Invalid securityQuestion/Answer");
				return "invalid";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public String forgotPin(String mobile, String Password) {
		try {
			UserInfo user = userDAO.findByMobile(mobile);

			if (BCrypt.checkpw(Password, user.getPassword())) {
				int randomPIN = (int) (Math.random() * 9000) + 1000;
				String val = "" + randomPIN;

				user.setPin(BCrypt.hashpw(val, BCrypt.gensalt(12)));
				userDAO.save(user);
				return "Your new Pin is " + val;
			} else {
				return "Enter Correct Password";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public EditUserDetails getUserDetails(String mobile) {
		logger.info("In WalletServiceImpl at function getUserDetails");
		try {
			if (userDAO.existsByMobile(mobile)) {
				EditUserDetails editUserDetails = new EditUserDetails();
				UserInfo user = userDAO.findByMobile(mobile);
				logger.info("User details fetched of " + user.getFirstName());

				editUserDetails.setFirstName(user.getFirstName());
				editUserDetails.setLastName(user.getLastName());
				editUserDetails.setSecurityA(user.getSecurityA());
				editUserDetails.setSecurityQ(user.getSecurityQ());
				editUserDetails.setUsername(user.getUsername());
				return editUserDetails;
			}
			logger.error("User doesn't exist");
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public String editUser(String mobile, EditUserDetails user) {
		logger.info("In WalletServiceImpl at function editUser");
		try {
			UserInfo user1 = userDAO.findByMobile(mobile);
			if (userDAO.existsByUsername(user.getUsername())) {
				if (!(user.getUsername().equals(user1.getUsername()))) {
					return "Username already exists";
				}

			}

			user1.setFirstName(user.getFirstName());
			user1.setLastName(user.getLastName());
			user1.setSecurityA(user.getSecurityA());
			user1.setSecurityQ(user.getSecurityQ());
			user1.setUsername(user.getUsername());
			userDAO.save(user1);
			logger.info("User Updated - " + user.getFirstName());
			return "updated successfully";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	public List<UserInfo> getAllUsers(String mobile) {

		try {
			List<UserInfo> userlist = new ArrayList<>();
			UserInfo isAdmin = userDAO.findByMobile(mobile);
			if (isAdmin.getRole().equals("admin")) {
				for (UserInfo info : userDAO.findAll()) {
					if (info.getRole().equals("user")) {
						userlist.add(info);
						logger.info("okay ruturaj");
					}
				}
			} else {
				
			}
			return userlist;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
//change to disableUser
	public boolean deleteuser(String mobile) {
		try {
			UserInfo user = userDAO.findByMobile(mobile);
			if (user.getRole().equalsIgnoreCase("admin")) {
				System.out.println("Admin record cannot be deleted");
				return false;
			} else {
				userDAO.delete(user);
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
//return objects always
	
	@Override
	public String bankToWallet(String mobile, long cardNumber, double amount) {
		try {
			logger.info("In WalletServiceImpl at function addMoney");
			if (userDAO.existsById(mobile)) {
				if (amount < 10001 && amount > 0) {
					UserInfo user = userDAO.getOne(mobile);
					user.addTransactionCard(new Transaction(), "Added Money to wallet",
							amount + " is added from card number " + cardNumber, amount, cardNumber);
					double totalBalance = user.getBalance();
					totalBalance = totalBalance + amount;
					user.setBalance(totalBalance);
					userDAO.save(user);
					System.out.println("success");
					return "Success! Your total balance is now " + totalBalance;
				} else {
					return "Invalid amount! Amount should be positive number less than 10001 ";
				}
			} else {
				logger.error(mobile + " this mobile number does not exist");
				return "This mobile number is not regidtered in our system";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}
//userId
	@Override
	public List<Transaction> viewAddMoneyHistory(String mobile) {

		try {
			logger.info("In WalletServiceImpl at function viewMoneyHistory");
			UserInfo user = userDAO.getOne(mobile);
			List<Transaction> trans = user.getTransactions();
			List<Transaction> addmon = trans.stream()
					.filter(c -> c.getTransactionType().equals("Added Money to wallet")).collect(Collectors.toList());

			if (addmon.isEmpty()) {
				logger.error("No add money history present for " + mobile);
				return null;
			}
			return addmon;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Cards> viewCards(String mobile) {
		try {
			logger.info("In WalletServiceImpl at function viewCards");
			UserInfo user = userDAO.getOne(mobile);
			List<Cards> cd = user.getCards();
			if (cd.isEmpty()) {
				logger.error("No cards present for " + mobile);
				return null;
			}
			return cd;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public String addCard(Cards card, String mobile) {

		try {
			logger.info("In WalletServiceImpl at function addCard");
			if (userDAO.existsById(mobile)) {
				long cardNo = card.getCardNumber();
				System.out.println(cardNo);
				if (cardsDAO.existsById(cardNo)) {
					logger.error("Card number already exist");
					return "Card No. Already Exists";
				} else {
					if (String.valueOf(cardNo).length() != 16) {
						return "Invalid card number it should be 16 digit";
					} else {
						UserInfo user = userDAO.getOne(mobile);
						card.setUserInfo(user);
						cardsDAO.save(card);
						return "Card Added Successfully";
					}
				}
			} else {
				logger.error(mobile + " this mobile number does not exist");
				return "Mobile no. not registered";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}

	}

	@Override
	public String deleteAllCards(String mobile) {
		try {
			logger.info("In WalletServiceImpl at function deleteAllCards");
			if (userDAO.existsById(mobile)) {
				UserInfo user = userDAO.getOne(mobile);
				List<Cards> cd = user.getCards();
				if (cd.isEmpty()) {
					return "No cards to delete";
				} else {
					cardsDAO.deleteInBatch(cd);
					return "All cards deleted";
				}
			} else {
				logger.error(mobile + " this mobile number does not exist");
				return "Mobile number does not exist";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public String deleteCard(long cardNumber,String mobile) {
		try {
			logger.info("In WalletServiceImpl at function deleteCard");
			if (cardsDAO.existsById(cardNumber)) {
				cardsDAO.deleteById(cardNumber);
				return "Card is succesfully deleted";
			} else {
				logger.error(cardNumber + " this card number does not exist");
				return "Card number does not exist";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public String transfer(String mobile, String receiver, double amount, String pin) {

		UserInfo userInfo;

		logger.info("In WalletDAOImpl at function transfer");
		try {
			userInfo = userDAO.getOne(mobile);
			if (mobile.equals(receiver)) {
				logger.error("Self transaction tried by mobile - " + mobile);
				return "Self transfer not Allowed";
			}
			if (userDAO.existsById(receiver)) {
				if (BCrypt.checkpw(pin, userInfo.getPin())) {
					UserInfo receiverUser = userDAO.getOne(receiver);
					if (userInfo.getBalance() >= amount) {
						userInfo.setBalance(userInfo.getBalance() - amount);
						receiverUser.setBalance(amount + receiverUser.getBalance());
						logger.info("Money transfer successful..");

						userInfo.addTransaction(new Transaction(), "Debit",
								amount + " is transfered to " + receiverUser.getMobile(), amount);
						receiverUser.addTransaction(new Transaction(), "Credit",
								amount + "+ is received from " + userInfo.getMobile(), amount);

						userDAO.save(userInfo);
						userDAO.save(receiverUser);

						return "Success! Your Current balance is " + userInfo.getBalance();
					} else {
						logger.error("Insufficient Balance in Wallet of mobile - " + mobile);
						return "Insufficient Balance in Wallet";
					}
				} else {
					logger.error("Incorrect password is entered for mobile - " + mobile);
					return "Enter correct password";
				}
			} else {
				logger.error("Receiver User Doesn't Exists with mobile - " + receiver);
				return "Receiver User Doesn't Exists. Enter correct Receiver Mobile Number";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Transaction> getTransaction(String mobile) {
		UserInfo userInfo;

		logger.info("In WalletDAOImpl at function getTransaction");
		try {
			userInfo = userDAO.getOne(mobile);
			logger.info("User information retrieved by mobile - " + mobile);
			logger.info("All transactions are fetched of mobile - " + mobile);
			System.out.println(userInfo.getTransactions());
			return userInfo.getTransactions();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public String walletToBank(String mobile, long cardNumber, double amount, String pin) {
		try {
			logger.info("In WalletServiceImpl at function walletToBank");
			if (userDAO.existsById(mobile)) {
				UserInfo user = userDAO.getOne(mobile);
				if (BCrypt.checkpw(pin, user.getPin())) {
					if (amount < user.getBalance() && amount > 0) {
						user.addTransactionCard(new Transaction(), "Added Money to Bank",
								amount + " is added to bank account with card number " + cardNumber, amount,
								cardNumber);
						double totalBalance = user.getBalance();
						totalBalance = totalBalance - amount;
						user.setBalance(totalBalance);
						userDAO.save(user);
						return "Success! Your total balance is now " + totalBalance;
					} else {
						return "Invalid amount! Amount should be less than " + user.getBalance();
					}
				} else {
					return "Transaction Failed Incorrect Pin";
				}
			} else {
				return "This mobile number is not registered in our system";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}


	@Override
	public String getRole(String mobile) {
		UserInfo user=userDAO.findByMobile(mobile);
		
		return user.getRole();
	}

}