package com.artisana.models;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
	private Long id;
    private String username;
    private String email;
    private String phone;
    private String address;
    private Role role;
    private String file_path;
    
    private List<ProductDTO> products;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
    
    
	  public UserDTO(User user) {
	        this.id = user.getId();
	        this.username = user.getUsername();
	        this.email = user.getEmail();
	        this.phone = user.getPhone();
	        this.address = user.getAddress();
	        this.file_path = user.getFile_path();
	        this.role = user.getRole();
//	        this.products = user.getProducts().stream().map(ProductDTO::new)
//					.collect(Collectors.toList());
//	         ... set other fields
	    }
	public List<ProductDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
