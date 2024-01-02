package com.bilgeadam.technicService.model;

public class Services {
	
	long id;
	String service;
	long desktop;
	long laptop;
	long mac;
	long duration;
	
	public Services() {
		
	}
	
	public Services(long id, String service, long desktop, long laptop, long mac, long duration) {
		this.id = id;
		this.service = service;
		this.desktop = desktop;
		this.laptop = laptop;
		this.mac = mac;
		this.duration = duration;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public long getDesktop() {
		return desktop;
	}

	public void setDesktop(long desktop) {
		this.desktop = desktop;
	}

	public long getLaptop() {
		return laptop;
	}

	public void setLaptop(long laptop) {
		this.laptop = laptop;
	}

	public long getMac() {
		return mac;
	}

	public void setMac(long mac) {
		this.mac = mac;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	
	
	
}
