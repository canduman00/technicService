package com.bilgeadam.technicService.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.technicService.model.Services;

@Repository
public class ServicesRepository {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public ServicesRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public List<Services> getall(){
		String sql = "select * from \"SERVICES\" order by \"ID\" asc";
		return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Services.class));
	}
	
	public Services getbyid(long id) {
		String sql = "select * from \"SERVICES\" where \"ID\" = :id";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Services.class));
	}
	
	public long getbydevice(String device, long id) {
		
			String sql = "select \""+ device +"\" from \"SERVICES\" where \"ID\"=:id";
			Map<String, Object> paramMap = new HashMap<>();
			//paramMap.put("device", device);
			paramMap.put("id", id);
			return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Long.class);
		
		
		
	}
	
}
