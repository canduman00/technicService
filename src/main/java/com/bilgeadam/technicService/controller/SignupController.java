package com.bilgeadam.technicService.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.technicService.model.SystemUser;
import com.bilgeadam.technicService.repository.UserRepository;

@RestController
@RequestMapping(path = "/signup")
public class SignupController {
	
	private UserRepository userRepository;
	
	public SignupController(UserRepository userRepository) {
		this.userRepository = userRepository;
		
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> signup(@RequestBody SystemUser systemUser){
		try {
			boolean result = userRepository.signup(systemUser);
			if(result) {
				return ResponseEntity.ok("kayit basari ile tamamlandi");
			}
			else {
				return ResponseEntity.internalServerError().body("kayit gerceklestirilemedi");
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("kayit sirasinda bir hata olustu -->" + e.getMessage());
		}
	}

}
