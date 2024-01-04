package com.bilgeadam.technicService.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.technicService.model.Sale;
import com.bilgeadam.technicService.repository.SaleRepository;

@RestController
@RequestMapping(path="sale")
public class SaleController {
	
	private SaleRepository saleRepo;
	
	public SaleController(SaleRepository saleRepo) {
		this.saleRepo = saleRepo;
	}
	
	//sale - admin parts
	
	@PostMapping(path="/admin/save", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody Sale sale){
		
		try {
			return ResponseEntity.ok(saleRepo.save(sale));
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("sale error --> " + e.getMessage());
		}
		
	}
	
	@DeleteMapping(path="/admin/deletebyid/{id}")
	public ResponseEntity<String> deletebyid(@PathVariable(name="id")long id){
		try {
			boolean result = saleRepo.deletebyid(id);
			if(result) {
				return ResponseEntity.ok("sale " + id + " deleted");
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("sale " + id + " not found");
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("sale error --> " + e.getMessage());
		}
	}
	
	//sale - public
	
	@GetMapping(path="/getall")
	public ResponseEntity<List<Sale>> getall(){
		return ResponseEntity.ok(saleRepo.getall());
	}
	
	@GetMapping(path="/like")
	public ResponseEntity<Object> getlike(@RequestParam(name="product")String product_name){
		try {
			List<Sale> result = saleRepo.getlike(product_name);
			if(result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a sale under " + product_name);
			}
			else {
				return ResponseEntity.ok(result);
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("sale error --> " +e.getMessage());
		}
	}
	
	//sale - user
	
	@PostMapping(path = "/user/buy", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> buy(@RequestBody Map<String, Object> json){
		try {
			Integer hold = (Integer) json.get("sale_id");
			long sale_id = hold.longValue();
			String credit_info = (String) json.get("credit_card");
			//credit card check
			if(credit_info == null) {
				return ResponseEntity.unprocessableEntity().body("credit_card cannot be empty");
			}
			boolean result = saleRepo.buy(sale_id, credit_info);
			if(result) {
				return ResponseEntity.ok("purchase successful");
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("sale id " + sale_id + " not found");
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body("purchase error -->" + e.getMessage() + " ----" + e.getLocalizedMessage() + "-----" + e.getClass());
		}
	}
	
}
