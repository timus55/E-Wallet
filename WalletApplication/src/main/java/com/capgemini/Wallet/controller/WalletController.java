package com.capgemini.Wallet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.Wallet.AuthenticationAadhar.AuthenticationRequestAadhar;
import com.capgemini.Wallet.AuthenticationNumber.AuthenticationRequest;
import com.capgemini.Wallet.AuthenticationNumber.AuthenticationResponse;
import com.capgemini.Wallet.AuthenticationUsername.AuthenticationRequestUsername;
import com.capgemini.Wallet.DAO.UserDAO;
import com.capgemini.Wallet.bean.EditUserDetails;
import com.capgemini.Wallet.bean.ForgotPassRequest;
import com.capgemini.Wallet.entities.*;
import com.capgemini.Wallet.service.IWalletService;
import com.capgemini.Wallet.util.JwtUtil;
import com.capgemini.Wallet.utility.GlobalResources;

/**
 * @author Shivam Hatwar, Sumeet Patil, Dinesh Bachchani,Sagar Tejwani, Ruturaj Patil
 * 		   Date of creation: 14/09/2020 
 * 		   This is the controller for managing services related to Wallet.
 * 		   This controller maps the requests coming from front end to back end.
 */

/**
 * 	The @RestController annotation was introduced in Spring 4.0 to simplify the creation of
 *  RESTful web services. It's a convenience annotation that combines @Controller and 
 *  @ResponseBody – which eliminates the need to annotate every request handling method of
 *  the controller class with the @ResponseBody annotation.
 */
@RestController

/**
 * The annotation in Spring Framework is used for mapping a particular HTTP request method to
 *  a specific class/ method in the controller that will be handling the respective request. 
 *  This annotation can be applied at both levels: Class level: Maps the URL of the request
 */
@RequestMapping("/")

/**
 *	Cross-Origin Resource Sharing (CORS) is a security concept that allows restricting the 
 *	resources implemented in web browsers. It prevents the JavaScript code producing or 
 *	consuming the requests against different origin
 */
@CrossOrigin(origins="*", allowedHeaders = "*")
public class WalletController {

	/**
	 *  Autowires IWalletService object (Dependency Injection)
	 */
	@Autowired
	private IWalletService service;
	
	/**
	 *  Autowires AuthenticationManager object (Dependency Injection)
	 */
	@Autowired
	AuthenticationManager authenticationManager;
	
	/**
	 *  Autowires JwtUtil object (Dependency Injection)
	 */
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	UserDAO userDAO;
	
	private Logger logger=GlobalResources.getLogger(WalletController.class);
	
	/**
	 * This method registers the new user
	 * @param user : UserInfo 
	 * @return String
	 * 
	 */
	@PostMapping("/register")
	public String addUser(@RequestBody UserInfo user)
	{
		return service.addUser(user);
	}
	
	/**
	 * This method authenticates the user
	 * @param authenticationRequest :  AuthenticationRequest
	 * @return ResponseEntity
	 * @throws Exception
	 * 
	 */
	
	@PostMapping("/authenticateNumber")
	public String  createAuthenticationTokenNumber(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
	{
		try
		{
			authenticationManager.authenticate
			(new UsernamePasswordAuthenticationToken
					
					(authenticationRequest.getMobile(),authenticationRequest.getPassword()));
			
		}
		catch(BadCredentialsException e)
		{
			throw new Exception ("incorrect detail");
		}
		final UserDetails userDetails = service.loadUserByUsername(authenticationRequest.getMobile());
		
		//final String jwt= jwtTokenUtil.generateToken(authenticationRequest.getMobile());
		System.out.println(authenticationRequest.getMobile());
		
		return jwtTokenUtil.generateToken(authenticationRequest.getMobile());
		//return ResponseEntity.ok(new AuthenticationResponse(jwt));
	
	}
	
	
	@PostMapping("/authenticateUsername")
	public String  createAuthenticationTokenUsername(@RequestBody AuthenticationRequestUsername authenticationRequest) throws Exception
	{
		try
		{
			System.out.println("try");
			System.out.println(authenticationRequest.getUsername());
			System.out.println(authenticationRequest.getPassword());
			
			authenticationManager.authenticate
			(new UsernamePasswordAuthenticationToken
					
					
					(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
			System.out.println("ok");
		}
		catch(BadCredentialsException e)
		{
			throw new Exception ("incorrect detail");
		}
		final UserDetails userDetails = service.loadUserByUsername(authenticationRequest.getUsername());
		System.out.println("return");
		UserInfo user=userDAO.findByUsername(authenticationRequest.getUsername());
		return jwtTokenUtil.generateToken(user.getMobile());
	}
	
	@PostMapping("/authenticateAadhar")
	public String  createAuthenticationTokenAadhar(@RequestBody AuthenticationRequestAadhar authenticationRequest) throws Exception
	{
		try
		{
			System.out.println(authenticationRequest.getAadhar());
			System.out.println(authenticationRequest.getPassword());
			
			authenticationManager.authenticate
			(new UsernamePasswordAuthenticationToken
					
					
					(authenticationRequest.getAadhar(),authenticationRequest.getPassword()));
			System.out.println("ok");
		}
		catch(BadCredentialsException e)
		{
			throw new Exception ("incorrect detail");
		}
		final UserDetails userDetails = service.loadUserByUsername(authenticationRequest.getAadhar());
		System.out.println("return");
		UserInfo user=userDAO.findByAadhar(authenticationRequest.getAadhar());
		return jwtTokenUtil.generateToken(user.getMobile());
	}
	
	
	/**
	 * This method is used to login by Mobile number
	 * @param user :  UserInfo
	 * @return String
	 * 
	 */
//	@PostMapping("/loginByNumber")
//	public String loginByNumber(@RequestBody UserInfo user)
//	{
//		return service.loginByNumber(user);
//	}
//	
//	/**
//	 * This method is used to login by Mobile number
//	 * @param user :  UserInfo
//	 * @return String
//	 * 
//	 */
//	@PostMapping("/loginByAadhar")
//	public String loginByAadhar(@RequestBody UserInfo user)
//
//	{
//		return service.loginByAadhar(user);
//	}
//	
//	/**
//	 * This method is used to login by Aadhar
//	 * @param  user :  UserInfo
//	 * @return String
//	 * 
//	 */
//	@PostMapping("/loginByUsername")
//	public String loginByUsername(@RequestBody UserInfo user)
//	{
//		return service.loginByUsername(user);
//	}
//	
//	/**
//	 * This method is used to login by Username
//	 * @param  user :  UserInfo
//	 * @return String
//	 * 
//	 */
	@GetMapping("/checkBalance")
	public ResponseEntity<Double> checkBalance(HttpServletRequest request)
	
	{
		
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
//		return new ResponseEntity<Boolean>(customerService.updateStatus(username,orderId,status), HttpStatus.OK);
		return new ResponseEntity<Double>(service.checkBalance(mobile),HttpStatus.OK);
	}
	
	/**
	 * This method is used to add Money from bank account to wallet
	 * @param request :  HttpServletRequest
	 * @param cardNumber :  long
	 * @param amount :  double
	 * @return String
	 * 
	 */
	@GetMapping("/addMoney/{cardNumber}/{amount}")
	public String addMoney(HttpServletRequest request, @PathVariable long cardNumber, @PathVariable double amount)
	{   
		logger.info("In WalletController at function addMoney");
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		return service.bankToWallet(mobile,cardNumber,amount);
		
	}
	
	/**
	 * This method is used to view add money History
	 * @param  request :  HttpServletRequest
	 * @return List<Transaction>
	 * 
	 */
	@GetMapping("/viewAddMoneyHistory") 
	public List<Transaction> viewAddMoneyHistory(HttpServletRequest request)
	{
		
		logger.info("In WalletController at function viewAddMoneyHistory");
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		return service.viewAddMoneyHistory(mobile);
	}
	
	/**
	 * This method is used to view cards
	 * @param  request :  HttpServletRequest
	 * @return List<Cards>
	 * 
	 */
	@GetMapping("/viewCards") 
	public List<Cards> viewCards(HttpServletRequest request)
	{
		logger.info("In WalletController at function viewCards");
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		
		return service.viewCards(mobile);
	}
	
	/**
	 * This method is used to add cards
	 * @param card :  Cards
	 * @param request :  HttpServletRequest
	 * @return List<Cards>
	 * 
	 */
	@PostMapping("/addCard")
	public String addCard(@RequestBody Cards card, HttpServletRequest request) 
	{
		logger.info("In WalletController at function addCards");
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		return service.addCard(card, mobile);
	}
	
	/**
	 * This method is used to delete single card
	 * @param cardNumber :  long
	 * @return String
	 * 
	 */
	@GetMapping("/deleteCard/{cardNumber}")
	public String deleteCard( @PathVariable Long cardNumber,HttpServletRequest request) 
	{
		logger.info("In WalletController at function deleteCard");
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		return service.deleteCard(cardNumber,mobile);
	}
	
	/**
	 * This method is used to delete single card
	 * @param request :  HttpServletRequest
	 * @return String
	 * 
	 */
	@GetMapping("/deleteAllCards")
	public String deleteAllCards(HttpServletRequest request) 
	{
		logger.info("In WalletController at function deleteCards");
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		return service.deleteAllCards(mobile);
	}
	
	/**
	 * This method is used to change password of the user
	 * @param request :  HttpServletRequest
	 * @param oldPassword :  String
	 * @param newPassword :  String
	 * @return ResponseEntity<String>
	 * 
	 */
	@GetMapping("/changePassword/{oldPassword}/{newPassword}")
	public ResponseEntity<String> checkPassword(HttpServletRequest request,
			@PathVariable("oldPassword") String oldPassword, @PathVariable("newPassword") String newPassword)
			throws Exception {
		logger.info("In WalletController at function changePassword");
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		return new ResponseEntity<String>(service.changePassword(mobile, oldPassword, newPassword), HttpStatus.OK);

	}

	/**
	 * This method is used when user forgets his password
	 * @param forgotpPassRequest :  ForgotPassRequest
	 * @return String
	 * 
	 */
	@PutMapping("/forgotPassword")
	public String forgotPassword(@RequestBody ForgotPassRequest forgotpPassRequest) throws Exception {
		logger.info("In WalletController at function forgotPassword");
		return service.forgotPassword(forgotpPassRequest.getMobile(), forgotpPassRequest.getSecurityQ(), forgotpPassRequest.getSecurityA());
	}
	
	/**
	 * This method is used when user forgets his Pin
	 * @param password :  String
	 * @return String
	 * 
	 */
	@GetMapping("/forgotPin/{password}")
	public String forgotPin(HttpServletRequest request,@PathVariable("password") String password) throws Exception {
		logger.info("In WalletController at function forgotPassword");
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		return service.forgotPin(mobile,password);
	}

	/**
	 * This method is used to get Particular user details
	 * @param request :  HttpServletRequest
	 * @return ResponseEntity<EditUserDetails>
	 * 
	 */
	@GetMapping(value = "/getUserDetails")
	public ResponseEntity<EditUserDetails> getUserDetails(HttpServletRequest request) {
		
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));

		return new ResponseEntity<EditUserDetails>(service.getUserDetails(mobile), HttpStatus.OK);
	}

	/**
	 * This method is used to edit user details
	 * @param request :  HttpServletRequest
	 * @param user :  EditUserDetails
	 * @return String
	 * 
	 */
	@PutMapping(value = "/editUser")
	public String editUser(HttpServletRequest request, @RequestBody EditUserDetails user) {
		logger.info("In WalletController at function editUser");
		
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		
		return service.editUser(mobile,user);
	}
	
	/**
	 * This method is used to get all user details
	 * @param request :  HttpServletRequest
	 * @return List<UserInfo>
	 * 
	 */
	@GetMapping("/getAll")
	public List<UserInfo> getAllUsers(HttpServletRequest request)
	{
		
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		
		//logger.info("In WalletController at function getAllUsers");
		return service.getAllUsers(mobile);
		
	}
	
	/**
	 * This method is used to delete user details
	 * @param mobile :  String
	 * @return boolean
	 * 
	 */
	@GetMapping(value = "/deleteUser/{mobile}")
	public boolean deleteuser(@PathVariable String mobile)
	{
		
		return service.deleteuser(mobile);
		
	}

	/**
	 * This method is used to Transfer funds from one user wallet to other
	 * @param request :  HttpServletRequest
	 * @param receiver :  String
	 * @param amount :  double
	 * @param pin :  pin
	 * @return String
	 * 
	 */
	@GetMapping(value = "/transfer/{receiver}/{amount}/{pin}")
	public String transfer(HttpServletRequest request,@PathVariable String receiver,
			@PathVariable double amount, @PathVariable String pin)
	{
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		logger.info("In WalletController at function transfer");
		return service.transfer(mobile, receiver, amount, pin);		
	}
	
	/**
	 * This method is used transfer back to bank from wallet
	 * @param request :  HttpServletRequest
	 * @param cardNumber :  long
	 * @param amount :  double
	 * @return String
	 * 
	 */
	@GetMapping("/withdrawMoney/{cardNumber}/{amount}/{pin}")
	public String walletToBank(HttpServletRequest request, @PathVariable long cardNumber, @PathVariable double amount, @PathVariable String pin)
	{   
		logger.info("In WalletController at function addMoney");
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		return service.walletToBank(mobile,cardNumber,amount,pin);
		
	}
	
	/**
	 * This method is used get List of all transactions
	 * @param request :  HttpServletRequest
	 * @return String
	 * 
	 */
	@GetMapping(value = "/getTransactions")
	public List<Transaction> getTransaction(HttpServletRequest request)
	{
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		
		logger.info("In WalletController at function getTransaction");
		return service.getTransaction(mobile);
	}
	
	@GetMapping("/getRole")
	public String getRole(HttpServletRequest request)
	{
		final String jwt = request.getHeader("Authorization");
		final String mobile = jwtTokenUtil.extractMobile(jwt.substring(7));
		logger.info("In WalletController at function getRole");
		return service.getRole(mobile);
	}

}
	
