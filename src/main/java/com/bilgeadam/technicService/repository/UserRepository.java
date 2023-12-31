package com.bilgeadam.technicService.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.technicService.model.SystemUser;

@Repository
public class UserRepository {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public boolean signup(SystemUser systemUser) {
		String sql = "insert into \"USERS\"(\"username\", \"password\", \"mail\") "
				+ "values(:username, :password, :mail)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", systemUser.getUsername());
		paramMap.put("password", systemUser.getPassword());
		paramMap.put("mail", systemUser.getMail());
		namedParameterJdbcTemplate.update(sql, paramMap);
		
		sql = "insert into \"ROLES\"(\"username\") values(:username)";
		paramMap = new HashMap<>();
		paramMap.put("username", systemUser.getUsername());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
		
		
	}
	
}
