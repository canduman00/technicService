package com.bilgeadam.technicService.model;

import java.time.LocalDate;

public class SaleLog {
	long sale_id;
	long user_id;
	String credit_card;
	LocalDate date;
	String product_type;
	String product_note;
	long price;
	
	public SaleLog() {
		
	}

	public SaleLog(long sale_id, long user_id, String credit_card, LocalDate date, String product_type,
			String product_note, long price) {
		this.sale_id = sale_id;
		this.user_id = user_id;
		this.credit_card = credit_card;
		this.date = date;
		this.product_type = product_type;
		this.product_note = product_note;
		this.price = price;
	}

	public long getSale_id() {
		return sale_id;
	}

	public void setSale_id(long sale_id) {
		this.sale_id = sale_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getCredit_card() {
		return credit_card;
	}

	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getProduct_note() {
		return product_note;
	}

	public void setProduct_note(String product_note) {
		this.product_note = product_note;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
	
}
