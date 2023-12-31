package com.bilgeadam.technicService.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.technicService.model.SystemUser;
import com.bilgeadam.technicService.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/signup")
@io.swagger.v3.oas.annotations.tags.Tag(description = "sign up endpoint", name = "Sign Up")
public class SignupController {
	
	private UserRepository userRepository;
	private final MessageSource messageSource;
	
	public SignupController(UserRepository userRepository, ResourceBundleMessageSource messageSource) {
		this.userRepository = userRepository;
		this.messageSource = messageSource;
		
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "successful sign up response 200, unsuccessful sign up response 500", summary="sign up function using username, password and email")
	public ResponseEntity<String> signup(Locale locale,@RequestBody SystemUser systemUser){
		try {
			boolean result = userRepository.signup(systemUser);
			if(result) {
				return ResponseEntity.ok(messageSource.getMessage("signup.save.success", null,locale));
			}
			else {
				return ResponseEntity.internalServerError().body(messageSource.getMessage("signup.save.fail",null, locale));
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("signup.save.error",null, locale) + e.getMessage());
		}
	}

}
