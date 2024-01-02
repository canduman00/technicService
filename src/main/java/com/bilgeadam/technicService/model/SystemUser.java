package com.bilgeadam.technicService.model;

public class SystemUser {
	
	long id;
	String username = "";
	String password = "";
	String mail = "";
	
	
	public SystemUser() {
		
	}
	public SystemUser(Integer id,String username, String password, String mail) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.mail = mail;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	
}
