package com.bilgeadam.technicService.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.technicService.model.Booking;
import com.bilgeadam.technicService.repository.BookingRepository;
import com.bilgeadam.technicService.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/booking")
@Tag(name="Booking", description="booking endpoint")
public class BookingController {
	
	private BookingRepository bookingRepo;
	private UserRepository userRepo;
	private final MessageSource messageSource;
	
	public BookingController(BookingRepository bookingRepo, UserRepository userRepo, ResourceBundleMessageSource messageSource) {
		this.bookingRepo = bookingRepo;
		this.userRepo = userRepo;
		this.messageSource = messageSource;
	}
	
	
	//Booking control - admin parts
	
	@GetMapping(path = "/admin/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="admins can view all bookings")
	public ResponseEntity<List<Booking>> getall(){
		return ResponseEntity.ok(bookingRepo.getall());
	}
	
	@GetMapping(path = "/admin/like", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="admins can view all bookings of a selected user by sending username")
	public ResponseEntity<Object> getlike(Locale locale,@RequestParam(name="username")String username){
		
		try {
			List<Booking> result = bookingRepo.getlike(username);
			if(result.isEmpty()) {
				Object[] params = new Object[1];
				params[0] = username;
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("booking.getlike.fail", params, locale));
			}
			else {
				return ResponseEntity.ok(result);
			}
		}

		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.error", null, locale) + e.getMessage() + e.getLocalizedMessage());
		}
		
	}
	
	@PostMapping(path = "/admin/updatebookingbyid/{id}", consumes = MediaType.TEXT_PLAIN_VALUE)
	@Operation(summary="admins can update booking status by sending booking id and new status")
	public ResponseEntity<String> updatestatus(Locale locale, @PathVariable(name="id")long booking_id, @RequestBody String status){
		try {
			boolean result;
			if(status.toUpperCase().equals("COMPLETED") || status.toUpperCase().equals("REPAIRING")) {
				result = bookingRepo.updatestatus(booking_id, status.toUpperCase());
			}
			else {
				return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(messageSource.getMessage("booking.update.fail", null, locale));
			}
			if(result) {
				Object[] params = new Object[2];
				params[0] = booking_id;
				params[1] = status.toUpperCase();
				return ResponseEntity.ok(messageSource.getMessage("booking.update.success", params, locale));
			}
			else {
				return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.update.error", null, locale));
			}
			
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	//Booking - user parts
	
	@PostMapping(path = "/user/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="users can create new booking by sending booking info")
	public ResponseEntity<String> save(Locale locale,@RequestBody Booking booking){
		try {
			return ResponseEntity.ok().body(bookingRepo.save(locale,booking));

		}
		catch(BadSqlGrammarException e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.save.device.fail", null, locale));
		}
		catch(EmptyResultDataAccessException e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.save.service.fail", null, locale));
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.error", null, locale) + e.getMessage() + "---" +  e.getLocalizedMessage() + "---" + e.getClass());
		}
	}
	
	@GetMapping(path = "/user/mybookings", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="users can view all bookings under their username")
	public ResponseEntity<Object> getuserbookings(Locale locale) {
		try {
			String username = userRepo.getSessionName();
			List<Booking> result = bookingRepo.getlike(username);
			if(result.isEmpty()) {
				Object[] params = new Object[1];
				params[0] = username;
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("booking.getlike.fail",params, locale));
			}
			else {
				return ResponseEntity.ok(result);
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.error", null,locale) + e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/user/deletebyid/{id}")
	@Operation(summary="users can delete their bookings by sending booking id")
	public ResponseEntity<String> deletebyid(Locale locale, @PathVariable(name="id")long id){
		try {
			boolean result = bookingRepo.deletebyid(id);
			Object[] params = new Object[1];
			params[0] = id;
			if(result) {
				return ResponseEntity.ok(messageSource.getMessage("booking.delete.success", params, locale));
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("booking.delete.fail", params, locale));
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("booking.error", null, locale) + e.getMessage());
		}
	}
	
	
	
}
