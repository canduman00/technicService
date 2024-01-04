package com.bilgeadam.technicService.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.bilgeadam.technicService.model.Role;
import com.bilgeadam.technicService.model.SystemUser;

@Repository
public class UserRepository {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private PasswordEncoder passwordEncoder;
	
	public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, PasswordEncoder passwordEncoder) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public String getSessionName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
	
	
	public boolean signup(SystemUser systemUser) {
		String sql = "insert into \"USERS\"(\"username\", \"password\", \"mail\") "
				+ "values(:username, :password, :mail)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", systemUser.getUsername());
		//paramMap.put("password", systemUser.getPassword());
		paramMap.put("password", passwordEncoder.encode(systemUser.getPassword()));
		paramMap.put("mail", systemUser.getMail());
		namedParameterJdbcTemplate.update(sql, paramMap);
		
		sql = "insert into \"ROLES\"(\"username\") values(:username)";
		paramMap = new HashMap<>();
		paramMap.put("username", systemUser.getUsername());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}
	
	public SystemUser getByUsername(String username) {
		String sql = "select * from \"USERS\" where \"username\" = :username";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", username);
		return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(SystemUser.class));
	}
	
	public List<GrantedAuthority> getUserRoles(String username){
		String sql = "select \"authority\" from \"ROLES\" where \"username\" = :username";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", username);
		List<String> liste = namedParameterJdbcTemplate.queryForList(sql, paramMap, String.class);
		List<GrantedAuthority> roles = new ArrayList<>();
		for(String role:liste) {
			roles.add(new Role(role));
		}
		return roles;
	}
	
}
