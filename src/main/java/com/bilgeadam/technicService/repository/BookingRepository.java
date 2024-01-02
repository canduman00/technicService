package com.bilgeadam.technicService.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.technicService.model.Booking;

@Repository
public class BookingRepository {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public BookingRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public List<Booking> getall(){
		String sql = "select * from \"BOOKING\" order by \"booking_id\" asc";
		return namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Booking.class));
		
	}
	
	public List<Booking> getlike(String username){
		String sql = "select * from \"BOOKING\" where \"username\" = :username  order by \"booking_id\" asc";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", username);
		return namedParameterJdbcTemplate.query(sql, paramMap,BeanPropertyRowMapper.newInstance(Booking.class));
	}
	
	public boolean updatestatus(long booking_id, String status) {
		String sql = "update \"BOOKING\" set \"status\" = :status where \"booking_id\" = :booking_id";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status", status);
		paramMap.put("booking_id", booking_id);
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}
	
}
