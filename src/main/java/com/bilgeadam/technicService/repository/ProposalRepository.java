package com.bilgeadam.technicService.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.technicService.model.Proposals;

@Repository
public class ProposalRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private UserRepository userRepo;
	
	public ProposalRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, UserRepository userRepo) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.userRepo = userRepo;
	}
	
	//proposals - user part
	
	public boolean save(Proposals proposal) {
		String sql = "insert into \"PROPOSALS\""
				+ "(\"username\", \"product_type\", \"product_note\", \"price\")"
				+ " values(:username, :product_type, :product_note, :price)";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", userRepo.getSessionName());
		switch(proposal.getProduct_type().toLowerCase()) {
		case "cpu":
		case "gpu":
		case "ram":
		case "motherboard":
			paramMap.put("product_type", proposal.getProduct_type().toLowerCase());
			break;
		default:
			return false;
		}
		
		paramMap.put("product_note", proposal.getProduct_note());
		paramMap.put("price", proposal.getPrice());
		
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}
	
	public List<Proposals> getuserall(){
		String sql = "select * from \"PROPOSALS\" where \"username\"=:username";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", userRepo.getSessionName());
		return namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Proposals.class));
	}
	
	public boolean deletebyid(long id) {
		String sql = "delete from \"PROPOSALS\" where \"ID\"=:id AND \"username\"=:username";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("username", userRepo.getSessionName());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}
	
	//proposals - admin part
	public List<Proposals> getall(){
		String sql = "select * from \"PROPOSALS\"";
		return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Proposals.class));
	}
	
	public Proposals getbyid(long id) {
		String sql = "select * from \"PROPOSALS\" where \"ID\"=:id";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Proposals.class));
	}
	
	public boolean updatebyid(Proposals proposal) {
		String sql = "update \"PROPOSALS\" set \"status\"=:status where \"ID\"=:id";
		Map<String, Object> paramMap = new HashMap<>();
		switch(proposal.getStatus().toUpperCase()) {
		case "ACCEPTED":
		case "REJECTED":
			paramMap.put("status", proposal.getStatus().toUpperCase());
			break;
		default:
			return false;
		}
		
		paramMap.put("id", proposal.getId());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
		//return getbyid(proposal.getId()).toString();
	}
	
}
