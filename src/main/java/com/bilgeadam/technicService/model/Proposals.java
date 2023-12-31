package com.bilgeadam.technicService.model;

public class Proposals {
	int id;
	String username;
	String product_type;
	String product_note;
	int price;
	String status;
	
	public Proposals() {
		
	}

	public Proposals(int id, String username, String product_type, String product_note, int price, String status) {
		this.id = id;
		this.username = username;
		this.product_type = product_type;
		this.product_note = product_note;
		this.price = price;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
