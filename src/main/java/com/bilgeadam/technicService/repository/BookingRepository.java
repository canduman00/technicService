package com.bilgeadam.technicService.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.bilgeadam.technicService.model.Booking;
import com.bilgeadam.technicService.model.Services;

@Repository
public class BookingRepository {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private ServicesRepository servicesRepo;
	
	public BookingRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, ServicesRepository servicesRepo){
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.servicesRepo = servicesRepo;
	}
	
	//Booking control - admin parts
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
	
	
	//Booking - user parts
	
	public boolean save(Booking booking) {
		
		//getting authenticated username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipal = auth.getName();
		
		Services services = servicesRepo.getbyid(booking.getService_id());
		
		
		//booking date function
		LocalDate date = LocalDate.now();
		String date_sql = "select sum(\"duration\") from \"BOOKING\" where \"booking_date\" = :date";
		Map<String, Object> paramMap = new HashMap<>();
		while(true) {
			paramMap.put("date", date);
			Object sum = namedParameterJdbcTemplate.queryForObject(date_sql, paramMap, Long.class);
			long sum2 = sum == null ? 0 : (long) sum;
			if((sum2 + services.getDuration()) >= 10 ) {
				date = date.plusDays(1);
			}
			else {
				break;
			}
		}
		
		//saving booking
		
		String booking_sql = "insert into \"BOOKING\"(\"username\", \"service_id\", \"device\", \"optional_note\", \"booking_date\", \"price\", \"duration\") "
				+ "values(:username, :service_id, :device, :optional_note, :booking_date, :price, :duration)";
		paramMap = new HashMap<>();
		paramMap.put("username", currentPrincipal);
		paramMap.put("service_id", booking.getService_id());
		paramMap.put("device", booking.getDevice());
		paramMap.put("optional_note", booking.getOptional_note());
		paramMap.put("booking_date", date);
		paramMap.put("price", servicesRepo.getbydevice(booking.getDevice(), booking.getService_id()));
		paramMap.put("duration", services.getDuration());
		
		return namedParameterJdbcTemplate.update(booking_sql, paramMap) == 1;
		
		
	}
	
}
