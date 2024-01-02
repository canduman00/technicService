package com.bilgeadam.technicService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.technicService.model.Booking;
import com.bilgeadam.technicService.repository.BookingRepository;

@RestController
@RequestMapping(path = "/booking")
public class BookingController {
	
	private BookingRepository bookingRepo;
	
	public BookingController(BookingRepository bookingRepo) {
		this.bookingRepo = bookingRepo;
	}
	
	
	//Booking control - admin parts
	
	@GetMapping(path = "/admin/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> getall(){
		return ResponseEntity.ok(bookingRepo.getall());
	}
	
	@GetMapping(path = "/admin/like", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getlike(@RequestParam(name="username")String username){
		
		try {
			List<Booking> result = bookingRepo.getlike(username);
			if(result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a booking reservation under " + username);
			}
			else {
				return ResponseEntity.ok(result);
			}
		}

		catch(Exception e) {
			return ResponseEntity.internalServerError().body("server error ---> " + e.getMessage() + e.getLocalizedMessage());
		}
		
	}
	
	@PostMapping(path = "/admin/updatebookingbyid/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> updatestatus(@PathVariable(name="id")long booking_id, @RequestBody String status){
		try {
			boolean result;
			if(status.toUpperCase().equals("COMPLETED") || status.toUpperCase().equals("REPAIRING")) {
				result = bookingRepo.updatestatus(booking_id, status.toUpperCase());
			}
			else {
				return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("status should be either 'REPAIRING' or 'COMPLETED'");
			}
			if(result) {
				return ResponseEntity.ok("booking_id " + booking_id+  " status changed into " + status.toUpperCase());
			}
			else {
				return ResponseEntity.internalServerError().body("status update error, check booking_id or status");
			}
			
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	//Booking - user parts
	
	@PostMapping(path = "/user/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody Booking booking){
		try {
			boolean result = bookingRepo.save(booking);
			if(result) {
				return ResponseEntity.ok().body("booking successful");
			}
			else {
				return ResponseEntity.internalServerError().body("booking error");
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("booking error, catch ---" + e.getMessage() + "---" +  e.getLocalizedMessage() + "---" + e.getClass());
		}
	}
	
	
}
