package com.bilgeadam.technicService.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8238710241357796652L;
	private String name;
	
	public Role(String name) {
		this.name = name;
	}
	
	@Override
	public String getAuthority() {
		return name;
	}
	@Override
	public String toString() {
		return name;
	}
	
}
