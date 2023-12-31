package com.bilgeadam.technicService.model;

import java.time.LocalDate;

public class Booking {
	int booking_id;
	String username;
	int service_id;
	String device;
	String optional_note;
	LocalDate date;
	int price;
	String status;
	
	public Booking() {
		
	}

	public Booking(int booking_id, String username, int service_id, String device, String optional_note, LocalDate date,
			int price, String status) {
		this.booking_id = booking_id;
		this.username = username;
		this.service_id = service_id;
		this.device = device;
		this.optional_note = optional_note;
		this.date = date;
		this.price = price;
		this.status = status;
	}

	public int getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getOptional_note() {
		return optional_note;
	}

	public void setOptional_note(String optional_note) {
		this.optional_note = optional_note;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
