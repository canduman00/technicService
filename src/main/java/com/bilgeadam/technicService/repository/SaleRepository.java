package com.bilgeadam.technicService.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.technicService.model.Sale;

@Repository
public class SaleRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SaleLogRepository saleLogRepo;
	
	
	public SaleRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, SaleLogRepository saleLogRepo) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.saleLogRepo = saleLogRepo;
	}
	
	public String save(Sale sale) {
		String sql = "insert into \"SALE\"(\"product_type\", \"product_note\", \"price\") "
				+ "values(:product_type, :product_note, :price)";
		Map<String, Object> paramMap = new HashMap<>();
		
		switch(sale.getProduct_type().toLowerCase()) {
		case "cpu":
		case "gpu":
		case "ram":
		case "motherboard":
			paramMap.put("product_type", sale.getProduct_type().toLowerCase());
			break;
		default:
			return "wrong product type";
		}
		
		paramMap.put("product_note", sale.getProduct_note());
		paramMap.put("price", sale.getPrice());
		
		namedParameterJdbcTemplate.update(sql, paramMap);
		
		return sale.toString();
	}
	
	public boolean deletebyid(long id) {
		String sql = "delete from \"SALE\" where \"ID\"=:id";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}
	
	
	public List<Sale> getall(){
		String sql = "select * from \"SALE\"";
		return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sale.class));
	}
	
	public List<Sale> getlike(String product_name){
		String sql = "select * from \"SALE\" where \"product_type\"=:product_type";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("product_type", product_name);
		return namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Sale.class));
	}
	
	public Sale getbyid(long id) {
		String sql = "select * from \"SALE\" where \"ID\"=:id";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		System.err.println("getbyid");
		return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Sale.class));
	}
	
	public boolean buy(long sale_id, String credit_info) {
		//System.err.println(credit_info);
		Sale sale;
		try {
			sale = getbyid(sale_id);
		}
		catch(EmptyResultDataAccessException e) {
			return false;
		}
		String sql = "delete from \"SALE\" where \"ID\"=:id";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", sale_id);
		boolean delete = namedParameterJdbcTemplate.update(sql, paramMap) == 1;
		boolean log;
		if(delete) {	
			//sale log
			//System.err.println("delete success");
			log = saleLogRepo.save(sale, credit_info);

		}else {
			//System.err.println("buy delete error");
			return delete;
		}
		
		return log;
	}
	
}
