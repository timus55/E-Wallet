package com.capgemini.Wallet.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capgemini.Wallet.service.IWalletService;
import com.capgemini.Wallet.service.WalletServiceImpl;
import com.capgemini.Wallet.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	@Autowired
	private IWalletService service;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		/*
		 * Will get the authorization header
		 */
		
		
		final String authorizationHeader =request.getHeader("Authorization");
		try
		{
			/*
			 * If authorization header is not null and starts with Bearer " then it will follow the next steps
			 * otherwise it will go to catch block.
			 * Extract the mobile number from jwt and then user details from that mobile number
			 * 
			 */
			String mobile=null;
			String jwt=null;
			if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer "))
			{
				jwt=authorizationHeader.substring(7);
				mobile=jwtUtil.extractMobile(jwt);
				
			}
			
			
			if(mobile !=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UserDetails userDetails=this.service.loadUserByUsername(mobile);
				
				if(jwtUtil.validateToken(jwt, userDetails))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					 usernamePasswordAuthenticationToken
                     .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
             SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
				}
			chain.doFilter(request, response);
			}
		
		/*
		 * Will throw an Exception if username and password is not valid
		 */
		catch( Exception exception) {
        	response.setContentType("application/json");
        	response.setCharacterEncoding("UTF-8");
        	response.setStatus(HttpServletResponse.SC_FORBIDDEN);			
			return;
	}
		}
		
	}

