package com.bilgeadam.technicService.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.technicService.model.Proposals;
import com.bilgeadam.technicService.repository.ProposalRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/proposal")
@Tag(name="Proposal", description="proposal endpoint")
public class ProposalController {
	
	private ProposalRepository proposalRepo;
	private final MessageSource messageSource;
	
	public ProposalController(ProposalRepository proposalRepo, ResourceBundleMessageSource messageSource) {
		this.proposalRepo = proposalRepo;
		this.messageSource = messageSource;
	}
	
	//proposal user parts
	
	@PostMapping(path = "/user/save", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="users can create proposal by sending proposal informations")
	public ResponseEntity<String> save(Locale locale, @RequestBody Proposals proposal){
		
		try {
			boolean result = proposalRepo.save(proposal);
			if(result) {
				return ResponseEntity.ok(messageSource.getMessage("proposal.save.success", null, locale));
			}
			else {
				return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.save.fail", null,locale));
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.error",null, locale) + e.getMessage());
		}
		
	}
	
	@GetMapping(path = "/user/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="users can view all proposals under their usernames")
	public ResponseEntity<Object> getuserall(Locale locale){
		try {
			List<Proposals> result = proposalRepo.getuserall();
			if(result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("proposal.getuserall.fail", null, locale));
			}
			else {
				return ResponseEntity.ok(result);
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.error", null,locale) + e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/user/deletebyid/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	@Operation(summary="users can delete their own proposal by sending proposal id")
	public ResponseEntity<String> deletebyid(Locale locale,@PathVariable(name="id")long id){
		try {
			boolean result = proposalRepo.deletebyid(id);
			Object[] params = new Object[1];
			params[0]=id;
			if(result) {
				return ResponseEntity.ok(messageSource.getMessage("proposal.delete.success", params, locale));
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("proposal.delete.fail", params, locale));
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.error", null,locale) + e.getMessage());
		}
	}
	
	//proposal admin parts
	@GetMapping(path = "/admin/getall", produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="admins can view all proposals")
	public ResponseEntity<List<Proposals>> getall(){
		return ResponseEntity.ok(proposalRepo.getall());
	}
	
	@GetMapping(path="/admin/getbyid/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="admins can get specific proposal by sending proposal id")
	public ResponseEntity<Object> getbyid(Locale locale,@PathVariable(name="id")long id){
		try {
			
			return ResponseEntity.ok(proposalRepo.getbyid(id));
		}
		catch(EmptyResultDataAccessException e) {
			Object[] params = new Object[1];
			params[0] = id;
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("proposal.delete.fail", params,locale));
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.error", null, locale) + e.getMessage());
		}
	}
	
	@PostMapping(path="/admin/update", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="admins can update proposal status by sending proposal id and new status")
	public ResponseEntity<String> update(Locale locale,@RequestBody Proposals proposal){
		try {
			boolean result = proposalRepo.updatebyid(proposal);
			if(result) {
				return ResponseEntity.ok(proposalRepo.getbyid(proposal.getId()).toString());
			}
			else {
				return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.save.fail",null, locale));
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("proposal.error",null, locale) + e.getMessage());
		}
	}
	
}
