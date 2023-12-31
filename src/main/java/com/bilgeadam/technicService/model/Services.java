package com.bilgeadam.technicService.model;

public class Services {
	
	int id;
	String service;
	String desktop;
	String laptop;
	String mac;
	int duration;
	
	public Services() {
		
	}
	
	public Services(int id, String service, String desktop, String laptop, String mac, int duration) {
		this.id = id;
		this.service = service;
		this.desktop = desktop;
		this.laptop = laptop;
		this.mac = mac;
		this.duration = duration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getDesktop() {
		return desktop;
	}

	public void setDesktop(String desktop) {
		this.desktop = desktop;
	}

	public String getLaptop() {
		return laptop;
	}

	public void setLaptop(String laptop) {
		this.laptop = laptop;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	
	
}
