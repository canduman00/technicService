package com.bilgeadam.technicService.model;

public class Sale {
	int id;
	String product_type;
	String product_note;
	int price;
	
	public Sale() {
		
	}

	public Sale(int id, String product_type, String product_note, int price) {
		this.id = id;
		this.product_type = product_type;
		this.product_note = product_note;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	
}
