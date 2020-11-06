package com.capgemini.Wallet.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.capgemini.Wallet.DAO.UserDAO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	
	/*
	 * Help to secure the token and sign the signature
	 */
	private String secret ="secret";
	
	@Autowired 
	private UserDAO userDAO;
	
//	 public String extractUsername(String token) {
//	        return extractClaim(token, Claims::getSubject);
//	    }
	 
	/*
	 * Will help us to extract the mobile number from jwt
	 */
	 public String extractMobile(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }
	 
	 /*
	  * Will set the expiration of token
	  */
	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	    private Claims extractAllClaims(String token) {
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }

	    /*
	     * Check that either the given jwt is expired or not
	     */
	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }
	    /*
	     * Help to generate the token. Will call createToken() to generate token
	     */

	    public String generateToken(String mobile) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, mobile);
	    }

	    /*
	     * Will generate token according to subject and will set the expiration of token and other detials
	     */
	    
	    private String createToken(Map<String, Object> claims, String subject) {

	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24*10 ))
	                .signWith(SignatureAlgorithm.HS256, secret).compact();
	    }
	    
	    /*
	     * Will validate the token
	     */

	    public Boolean validateToken(String token, UserDetails userDetails) {
	    	
	        final String username = extractMobile(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    	
	    }
	
	
}
