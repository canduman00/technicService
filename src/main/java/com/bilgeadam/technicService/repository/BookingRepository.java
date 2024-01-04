package com.bilgeadam.technicService.repository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
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
	private UserRepository userRepo;
	private final MessageSource messageSource;
	
	public BookingRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, ServicesRepository servicesRepo, UserRepository userRepo, ResourceBundleMessageSource messageSource){
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.servicesRepo = servicesRepo;
		this.userRepo = userRepo;
		this.messageSource = messageSource;
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
	
	//getting authenticated username
	/*public String getSessionName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}*/
	
	
	public String save(Locale locale,Booking booking) {
		String currentPrincipal = userRepo.getSessionName(); 
		Services services = servicesRepo.getbyid(booking.getService_id());
		
		
		
		//booking date function
		LocalDate date = LocalDate.now(); 
				
		String date_sql = "select sum(\"duration\") from \"BOOKING\" where \"booking_date\" = :date";
		Map<String, Object> paramMap = new HashMap<>();
		while(true) {
			paramMap.put("date", date);
			Object sum = namedParameterJdbcTemplate.queryForObject(date_sql, paramMap, Long.class);
			long sum2 = sum == null ? 0 : (long) sum;
			if((sum2 + services.getDuration()) > 10 ) {
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
		long price = servicesRepo.getbydevice(booking.getDevice().toLowerCase(), booking.getService_id());
		paramMap.put("price", price);
		paramMap.put("duration", services.getDuration());
				
		namedParameterJdbcTemplate.update(booking_sql, paramMap);
		
		//internationalizaton
		Object[] params = new Object[2];
		params[0] = date;
		params[1] = price;
		
		return messageSource.getMessage("bookrepo.save.success", params ,locale);
		//return "Your booking generated on " + date + " with the price of " + price;
		
		
		}
	
		//delete booking by id
		
		public boolean deletebyid(long id) {
			String sql = "delete from \"BOOKING\" where \"booking_id\" = :id AND \"username\" = :username";
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("id", id);
			paramMap.put("username", userRepo.getSessionName());
			return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
		}
	
		
	}
	

