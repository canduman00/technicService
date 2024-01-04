package com.bilgeadam.technicService.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.technicService.model.Sale;

@Repository
public class SaleLogRepository {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private UserRepository userRepo;
	
	public SaleLogRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserRepository userRepo) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.userRepo = userRepo;
	}
	
	public boolean save(Sale sale, String credit_info) {
		//System.err.println("sale log repo");
		String sql = "insert into \"SALE_LOG\""
				+ "(\"sale_id\", \"user_id\", \"credit_card\", \"sale_date\", \"product_type\", \"product_note\", \"price\") "
				+ "values(:sale_id, :user_id, :credit_card, :sale_date, :product_type, :product_note, :price)";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("sale_id", sale.getId());
		paramMap.put("user_id", userRepo.getByUsername(userRepo.getSessionName()).getId());
		paramMap.put("credit_card", credit_info);
		LocalDate date = LocalDate.now();
		paramMap.put("sale_date", date);
		paramMap.put("product_type", sale.getProduct_type());
		paramMap.put("product_note", sale.getProduct_note());
		paramMap.put("product_note", sale.getProduct_note());
		paramMap.put("price", sale.getPrice());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}
	
	
}
