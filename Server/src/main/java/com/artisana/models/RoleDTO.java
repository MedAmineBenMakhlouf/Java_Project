package com.artisana.models;

public class RoleDTO {

	private Long id;
	private String name;
	
	 public RoleDTO(Role role) {
	        this.id = role.getId();
	        this.name = role.getName();
	 }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	 

}
