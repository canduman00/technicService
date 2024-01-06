package com.bilgeadam.technicService.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path="sale")
@Tag(description="sale endpoint", name="Sale")
public class SaleController {
	
	private SaleRepository saleRepo;
	private final MessageSource messageSource;
	
	public SaleController(SaleRepository saleRepo, ResourceBundleMessageSource messageSource) {
		this.saleRepo = saleRepo;
		this.messageSource = messageSource;
	}
	
	//sale - admin parts
	
	@PostMapping(path="/admin/save", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(description="successful save response 200, unsuccessful save response 500", summary="admins can create new sale by sending sale infos")
	public ResponseEntity<String> save(Locale locale,@RequestBody Sale sale){
		
		try {
			return ResponseEntity.ok(saleRepo.save(sale));
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("sale.error", null, locale) + e.getMessage());
		}
		
	}
	
	@DeleteMapping(path="/admin/deletebyid/{id}")
	@Operation(description="successful delete reponse 200, not found response 404, error response 500", summary="admins can delete sales by sending id")
	public ResponseEntity<String> deletebyid(Locale locale, @PathVariable(name="id")long id){
		try {
			Object[] params = new Object[1];
			params[0] = id;
			boolean result = saleRepo.deletebyid(id);
			if(result) {
				return ResponseEntity.ok(messageSource.getMessage("sale.delete.success", params, locale));
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("sale.delete.fail", params, locale));
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("sale.error", null, locale) + e.getMessage());
		}
	}
	
	//sale - public
	
	@GetMapping(path="/getall")
	@Operation(summary="public can view all sales")
	public ResponseEntity<List<Sale>> getall(){
		return ResponseEntity.ok(saleRepo.getall());
	}
	
	@GetMapping(path="/like")
	@Operation(description="success response 200, not found respone 404, error response 500", summary="public can search sales with product name")
	public ResponseEntity<Object> getlike(Locale locale,@RequestParam(name="product")String product_name){
		try {
			List<Sale> result = saleRepo.getlike(product_name);
			Object[] params = new Object[1];
			params[0] = product_name;
			if(result.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("sale.getlike.fail", params,locale));
			}
			else {
				return ResponseEntity.ok(result);
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("sale.error", null, locale) +e.getMessage());
		}
	}
	
	//sale - user
	
	@PostMapping(path = "/user/buy", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary="user can buy a product by sending sale_id and credit_card info")
	public ResponseEntity<String> buy(Locale locale,@RequestBody Map<String, Object> json){
		try {
			Integer hold = (Integer) json.get("sale_id");
			long sale_id = hold.longValue();
			String credit_info = (String) json.get("credit_card");
			Object[] params = new Object[1];
			params[0] = sale_id;
			//credit card check
			if(credit_info == null) {
				return ResponseEntity.unprocessableEntity().body(messageSource.getMessage("sale.buy.creditErro", null, locale));
			}
			boolean result = saleRepo.buy(sale_id, credit_info);
			if(result) {
				return ResponseEntity.ok(messageSource.getMessage("sale.buy.success", null, locale));
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("sale.buy.fail", params, locale));
			}
		}
		catch(Exception e) {
			return ResponseEntity.internalServerError().body(messageSource.getMessage("purchase.error", null, locale) + e.getMessage() + " ----" + e.getLocalizedMessage() + "-----" + e.getClass());
		}
	}
	
}
