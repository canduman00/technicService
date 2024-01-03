package com.bilgeadam.technicService.model;

import java.util.Date;

public class Booking {
	long booking_id;
	String username;
	long service_id;
	String device;
	String optional_note = "";
	Date booking_date;
	long price;
	String status;
	
	public Booking() {
		
	}

	public Booking(long booking_id, String username, long service_id, String device, String optional_note, Date booking_date,
			long price, String status) {
		this.booking_id = booking_id;
		this.username = username;
		this.service_id = service_id;
		this.device = device;
		this.optional_note = optional_note;
		this.booking_date = booking_date;
		this.price = price;
		this.status = status;
	}

	public long getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(long booking_id) {
		this.booking_id = booking_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getService_id() {
		return service_id;
	}

	public void setService_id(long service_id) {
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

	public Date getDate() {
		return booking_date;
	}

	public void setDate(Date date) {
		this.booking_date = date;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Booking [booking_id=" + booking_id + ", username=" + username + ", service_id=" + service_id
				+ ", device=" + device + ", optional_note=" + optional_note + ", date=" + booking_date + ", price=" + price
				+ ", status=" + status + "]";
	}
	
	
	
	
}
