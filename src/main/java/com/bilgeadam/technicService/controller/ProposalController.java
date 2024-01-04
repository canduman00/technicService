package com.bilgeadam.technicService.controller;

import java.util.List;

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

@RestController
@RequestMapping(path = "/proposal")
public class ProposalController {
	
	private ProposalRepository proposalRepo;
	
	public ProposalController(ProposalRepository proposalRepo) {
		this.proposalRepo = proposalRepo;
	}
	
	//proposal user parts
	
	@PostMapping(path = "/user/save", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody Proposals proposal){
		
		try {
			boolean result = proposalRepo.save(proposal);
			if(result) {
				return ResponseEntity.ok("proposal sent");
			}
			else {
				return ResponseEntity.internalServerError().body("wrong proposal format");
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("proposal error --> " + e.getMessage());
		}
		
	}
	
	@GetMapping(path = "/user/getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getuserall(){
		try {
			List<Proposals> result = proposalRepo.getuserall();
			if(result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("you don't have any proposals yet.");
			}
			else {
				return ResponseEntity.ok(result);
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("proposal error --> " + e.getMessage());
		}
	}
	
	@DeleteMapping(path = "/user/deletebyid/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deletebyid(@PathVariable(name="id")long id){
		try {
			boolean result = proposalRepo.deletebyid(id);
			if(result) {
				return ResponseEntity.ok("proposal " +id+ " deleted");
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("proposal " + id + " not found");
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("proposal error --> " + e.getMessage());
		}
	}
	
	//proposal admin parts
	@GetMapping(path = "/admin/getall", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Proposals>> getall(){
		return ResponseEntity.ok(proposalRepo.getall());
	}
	
	@GetMapping(path="/admin/getbyid/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getbyid(@PathVariable(name="id")long id){
		try {
			
			return ResponseEntity.ok(proposalRepo.getbyid(id));
		}
		catch(EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("proposal " + id + " not found");
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("proposal error --> " + e.getMessage());
		}
	}
	
	@PostMapping(path="/admin/update", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody Proposals proposal){
		try {
			boolean result = proposalRepo.updatebyid(proposal);
			if(result) {
				return ResponseEntity.ok(proposalRepo.getbyid(proposal.getId()).toString());
			}
			else {
				return ResponseEntity.internalServerError().body("wrong proposal format");
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("proposal error --> " + e.getMessage());
		}
	}
	
}
