package com.bilgeadam.technicService.model;

public class Sale {
	long id;
	String product_type;
	String product_note;
	long price;
	
	public Sale() {
		
	}

	public Sale(long id, String product_type, String product_note, long price) {
		this.id = id;
		this.product_type = product_type;
		this.product_note = product_note;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Sale [product_type=" + product_type + ", product_note=" + product_note + ", price="
				+ price + "]";
	}
	
	
}
