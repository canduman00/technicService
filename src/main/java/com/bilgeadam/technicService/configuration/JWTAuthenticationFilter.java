package com.bilgeadam.technicService.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bilgeadam.technicService.model.SystemUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		//login
		try {
			SystemUser creds = new ObjectMapper().readValue(req.getInputStream(), SystemUser.class );
			//System.err.println(creds.getUsername() + " - " + creds.getPassword());
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<GrantedAuthority>()));
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
		//MY_SECRET_KEY
		User principal = (User) auth.getPrincipal();
		String rolestring = principal.getAuthorities().toArray()[0].toString();
		String str = principal.getUsername() + "-" + rolestring;
		//token expires in 15 minute
		String token = JWT.create().withSubject(str).withExpiresAt(new Date(System.currentTimeMillis() + 900000)).sign(Algorithm.HMAC512("MY_SECRET_KEY".getBytes()));
		
		String body = "{\"username\":\"" + principal.getUsername() + "\",\"token\":\"" + token + "\"}";
		System.err.println(body);
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.getWriter().write(body);
		res.getWriter().flush();	
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException {
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		if(failed.getClass() == BadCredentialsException.class) {
			System.err.println("wrong credentials!");
			res.getWriter().write("wrong username or password");
		}
		else {
			res.getWriter().write("system error");
		}
		res.getWriter().flush();
	}
	
	
}
