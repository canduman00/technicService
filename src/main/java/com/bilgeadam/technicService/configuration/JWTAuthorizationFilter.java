package com.bilgeadam.technicService.configuration;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bilgeadam.technicService.model.Role;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter{
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		String token = req.getHeader("Authorization");
		System.err.println("token control ----" + this.getClass());
		
		if(token != null) {
			try {
				//jwt control
				String user = JWT.require(Algorithm.HMAC512("MY_SECRET_KEY".getBytes())).build().verify(token.replace("Bearer", "")).getSubject();
				System.err.println("----------> username and role = " + user);
				
				if(user != null) {
					//user-USER
					//admin-ADMIN
					String username = user.split("-")[0];
					Role auth = new Role(user.split("-")[1]);
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(auth));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					chain.doFilter(req, res);
				}
			}
			catch(Exception e) {
				System.err.println(e.getMessage());
				res.setStatus(HttpStatus.UNAUTHORIZED.value());
				res.getWriter().write("Token exception =>" + e.getMessage());
			}
		}
		else {
			System.err.println("no token but security config is active");
			chain.doFilter(req, res);
		}
		
	}
	
}
