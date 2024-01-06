package com.bilgeadam.technicService.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeConfig {
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http, @Autowired AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		//http.authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll());
		//http.authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated());
		
		http.authorizeHttpRequests(customizer -> customizer
				.requestMatchers("/booking/user/**").hasAuthority("ROLE_USER")
				.requestMatchers("/booking/admin/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers("/sale/user/**").hasAuthority("ROLE_USER")
				.requestMatchers("/sale/admin/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers("/proposal/user/**").hasAuthority("ROLE_USER")
				.requestMatchers("proposal/admin/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers("swagger-ui/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().permitAll());
		
		
		
		http.csrf(customizer -> customizer.disable());
		http.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
		http.addFilterAfter(new JWTAuthorizationFilter(), JWTAuthenticationFilter.class);
		return http.build();
	}
}
