package com.capgemini.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.annotation.Rollback;

import com.capgemini.Wallet.DAO.CardsDAO;
import com.capgemini.Wallet.DAO.UserDAO;
import com.capgemini.Wallet.bean.EditUserDetails;
import com.capgemini.Wallet.controller.WalletController;
import com.capgemini.Wallet.entities.Cards;
import com.capgemini.Wallet.entities.Transaction;
import com.capgemini.Wallet.entities.UserInfo;
import com.capgemini.Wallet.service.WalletServiceImpl;
import com.capgemini.Wallet.utility.GlobalResources;

@SpringBootTest
class WalletApplicationTests {

	@Test
	void contextLoads() {
	}

	private static Logger Logger = GlobalResources.getLogger(WalletApplicationTests.class);

	@Autowired
	WalletServiceImpl service;

	@Autowired
	WalletController controller;

	@Autowired
	UserDAO userDAO;

	@Autowired
	CardsDAO cardsDAO;

	UserInfo user = new UserInfo();

	Cards cards = new Cards();

	@BeforeAll
	static void setUpBeforeClass() {

		Logger.info("SetUpClass called");
		System.out.println("Checking resources for testing");
	}

	@BeforeEach
	void setup() {
		user.setAadhar("222222222222");
		user.setBalance(1000.00);
		user.setFirstName("dinesh");
		user.setLastName("bachchani");
		user.setMobile("7226936935");
		user.setUsername("dinesh@gmail.com");
		String hashNewPassword = BCrypt.hashpw("Dinesh@123", BCrypt.gensalt(12));
		user.setPassword(hashNewPassword);
		user.setRole("user");
		user.setSecurityQ("language");
		user.setSecurityA("java");
		user.setPin("1212");
		cards.setCardNumber(1212121212121212L);
		cards.setCvv(121);
		cards.setExpMonth(03);
		cards.setExpYear(2022);
		cards.setHolderName("Dinesh");
		List<Cards> list = new ArrayList<>();
		list.add(cards);
		user.setCards(list);
		userDAO.save(user);

//		service.addUser(new UserInfo("7226936936","dinesh","bachchani","dineshbachchani@gmail.com","Dinesh@123","user",1000.00,222222222222L,"language","java","1212"));
		Logger.info("Test Case Started");

		System.out.println("Test Case Started");
	}

	@AfterEach
	void tearDown() {
		Logger.info("Test Case Over");
		System.out.println("Test Case Over");
	}

	@Test
	@DisplayName("User Registration Unsuccessfully(Duplicate Mobile Number")
	@Rollback(true)
	public void createUserFirst() throws Exception {
		String methodName = "addUser()";
		Logger.info(methodName + "called");

		String message = service
				.addUser(new UserInfo("7226936935", "dinesh", "bachchani", "dineshbachchani0109@gmail.com",
						"Dinesh@123", "user", 1000.00, "333333333332", "language", "java", "1212"));
		String expectedMessage = "Duplicate mobile number Try login/ forgot password";
		assertEquals(message, expectedMessage);
	}

	@Test
	@DisplayName("User Registration Unsuccessfully(Duplicate username")
	@Rollback(true)
	public void createUserSecond() throws Exception {
		String methodName = "addUser()";
		Logger.info(methodName + "called");

		String message = service.addUser(new UserInfo("7226936937", "dinesh", "bachchani", "dineshbachchani@gmail.com",
				"Dinesh@123", "user", 1000.00, "333333333332", "language", "java", "1212"));
		String expectedMessage = "Username Already Exists.Try again with different Username...";
		assertEquals(message, expectedMessage);
	}

	@Test
	@DisplayName("User Registration Unsuccessfully(Duplicate aadhar card")
	@Rollback(true)
	public void createUserThird() throws Exception {
		String methodName = "addUser()";
		Logger.info(methodName + "called");

		String message = service
				.addUser(new UserInfo("7226936937", "dinesh", "bachchani", "dineshbachchani07@gmail.com", "Dinesh@123",
						"user", 1000.00, "222222222222", "language", "java", "1212"));
		String expectedMessage = "Username Already Exists.Try again with different Username...";
		assertEquals(message, expectedMessage);
	}

	@Test
	@DisplayName("User Registration successfully")
	@Rollback(true)
	public void createUserFourth() throws Exception {
		String methodName = "addUser()";
		Logger.info(methodName + "called");

		String message = service
				.addUser(new UserInfo("7226936939", "dinesh", "bachchani", "dineshbachchani072111@gmail.com",
						"Dinesh@123", "user", 1000.00, "222222232232", "language", "java", "1212"));
		assertNotNull(message);
	}

//	@Test
//	@DisplayName("User Login Successful By Mobile")
//	@Rollback(true)
//	public void loginByNumberSuccess() throws Exception {
//		user.setPassword("Dinesh@123");
//		String message = service.loginByNumber(user);
//		String expectedMessage = "7226936935";
//		assertEquals(message, expectedMessage);
//
//	}
//
//	@Test
//	@DisplayName("User Login UnSuccessful By Mobile")
//	@Rollback(true)
//	public void loginByNumberFail() throws Exception {
//		// user.setPassword("DD2@12112");
//		String message = service.loginByNumber(user);
//		String expectedMessage = "Failed";
//		assertEquals(message, expectedMessage);
//
//	}
//
////	
////	
//	@Test
//	@DisplayName("User Login Successful By Username")
//	@Rollback(true)
//	public void loginByUsernameSuccess() throws Exception {
//		user.setPassword("Dinesh@123");
//		String message = service.loginByUsername(user);
//		String expectedMessage = "7226936935";
//		assertEquals(message, expectedMessage);
//
//	}
//
////	
//	@Test
//	@DisplayName("User Login UnSuccessful By username")
//	@Rollback(true)
//	public void loginByUsernameFail() throws Exception {
//		// user.setPassword("DD2@12112");
//		String message = service.loginByNumber(user);
//		String expectedMessage = "Failed";
//		assertEquals(message, expectedMessage);
//
//	}
//
////	
//	@Test
//	@DisplayName("User Login Successful By Aadhar")
//	@Rollback(true)
//	public void loginByAadharSuccess() throws Exception {
//		user.setPassword("Dinesh@123");
//		String message = service.loginByNumber(user);
//		String expectedMessage = "7226936935";
//		assertEquals(message, expectedMessage);
//
//	}
//
////	
//	@Test
//	@DisplayName("User Login UnSuccessful By Aadhar")
//	@Rollback(true)
//	public void loginByAadharFail() throws Exception {
//		// user.setPassword("DD2@12112");
//		String message = service.loginByNumber(user);
//		String expectedMessage = "Failed";
//		assertEquals(message, expectedMessage);
//
//	}

//	
	@Test
	@DisplayName("Check Balance of User By Mobile")
	@Rollback(true)
	public void checkBalanceTest() throws Exception {
		Logger.info("Test Case - Get Balance of User By Mobile");
		double message = service.checkBalance("7226936935");
		double expectedMessage = 1000.00;
		assertEquals(message, expectedMessage);
	}

	@Test
	@DisplayName("Change Password Successfull")
	@Rollback(true)
	public void changePasswordFirstTest() {
		Logger.info("Test Case - Change Password Successfull");
		String expectedMessage = "Changed Password successfully";
		String message = service.changePassword("7226936935", "Dinesh@123", "Dinesh@12345");
		assertEquals(message, expectedMessage);
	}

	@Test
	@DisplayName("Change Password Unsuccessfull - Current Password is Wrong")
	@Rollback(true)
	public void changePasswordSecondTest() throws Exception {
		Logger.info("Test Case - Change Password Unsuccessfull - Current Password is Wrong");
		String message = service.changePassword("7226936935", "Virat@1234", "Rohit@12345");
		String expectedMessage = "Incorrect current Password";
		assertEquals(message, expectedMessage);
	}

	@Test
	@DisplayName("Forgot Password Successfull")
	@Rollback(true)
	public void forgotPasswordFirstTest() throws Exception {
		Logger.info("Test Case - Forgot Password Successfull");
		String message = service.forgotPassword("7226936935", "language", "java");
		String unexpected = "invalid";
		assertNotEquals(unexpected, message);
	}

	@Test
	@DisplayName("Forgot Password Fails - Incorrect security/password")
	@Rollback(true)
	public void forgotPasswordSecondTest() throws Exception {
		Logger.info("Forgot Password Fails - Incorrect security/password");
		String message = service.forgotPassword("7226936935", "language", "java	");
		String expected = "invalid";
		assertEquals(expected, message);
	}

	@Test
	@DisplayName("Get customer details")
	@Rollback(true)
	public void getUserDetailsTest() throws Exception {
		Logger.info("Get customer details");
		EditUserDetails user = service.getUserDetails("7226936935");
		assertEquals(user.getFirstName(), "dinesh");
	}

	@Test
	@DisplayName("Get customer details fails")
	@Rollback(true)
	public void getUserDetailsSecondTest() throws Exception {
		Logger.info("Get customer details fails");
		EditUserDetails user = service.getUserDetails("7226936931");
		assertEquals(user, null);
	}
	
	@Test
	@DisplayName("Add Cards")
	@Rollback(true)
	public void addCards() throws Exception {
	cards.setCardNumber(1111111111111111L);
	cards.setCvv(121);
	cards.setExpMonth(04);
	cards.setExpYear(2022);
	cards.setHolderName("Dinesh");
	cardsDAO.save(cards);
	String message=service.addCard(cards, "7226936935");
	String expectedMessage="Card Added Successfully";
	assertEquals(message,expectedMessage);
	}
	
	

	@Test
	@DisplayName("get transaction")
	@Rollback(true)
	public void transacation() {
		String methodName = "addTransaction()";
		Logger.info(methodName + "of service class called");
		Transaction transactions = new Transaction();
		transactions.setStatement("Amount debited");
		String response = null;
		try {
			Transaction status = null;
			if (status == service.getTransaction("7226936935")) {
				response = "success";
			}
		} catch (Exception e) {
			response = e.getMessage();
		}
		String expectedResponse = "success";
		assertNotEquals(response, expectedResponse);

	}
	
	@Test
	@DisplayName("deleteUser fails")
	@Rollback(true)
	public void deleteUser() throws Exception
	{
		user.setBalance(1000.00);
		user.setFirstName("ruturaj");
		user.setLastName("patil");
		user.setMobile("8787878787");
		user.setUsername("ruturaj@gmail.com");
		String hashNewPassword = BCrypt.hashpw("Dinesh@123", BCrypt.gensalt(12));
		user.setPassword(hashNewPassword);
		user.setRole("admin");
		user.setSecurityQ("language");
		user.setSecurityA("java");
		user.setPin("1212");
		userDAO.save(user);
		boolean message=service.deleteuser("8787878787");
		boolean expectedMessage=false;
		assertEquals(message,expectedMessage);
	}

}
