package com.capgemini.Wallet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capgemini.Wallet.filter.JwtRequestFilter;
import com.capgemini.Wallet.service.WalletServiceImpl;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	@Autowired 
	private WalletServiceImpl userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	/*
	 * AuthenticationManagerBuilder will call the service class and then loadByUsername() will call
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	/*
	 * it will diable the cross site request forgery. It will allow "/authenticate","/register",/forgotPassword"
	 * API to call without authentication and rest of the API will need authentication.
	 * HTTP will have stateless session policy
	 */
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.cors();
	http.csrf().disable().authorizeRequests().antMatchers("/authenticateNumber","/authenticateUsername","/authenticateAadhar","/register","/forgotPassword")
	.permitAll().anyRequest().authenticated()
	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	/*
	 * After spring boot 2.0, we need to add this bean and just return with super();
	 */
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
	
	
	/*
	 * This function will tell which password encoding technique we are using. 
	 * In our case, we are going for BCrypt.
	 */
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	
}
