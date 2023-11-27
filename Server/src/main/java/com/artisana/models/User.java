package com.artisana.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String custumerId;
	
	// NEW
	@Size(min = 3,max=50)
	private String username;
	
	@NotEmpty(message = "Email is required!")
	@Email(message = "Please enter a valid email!")
	private String email;
	
	@NotEmpty
	@NotNull
	@Size(min = 8,max=12)
	private String phone;
	
	@NotNull
	@Size(min = 8,max=50)
	private String address;
	
	@Size(min = 5)
	private String password;
	@Transient
	private String passwordConfirmation;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	
	//RELATIONS
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	@JsonIgnore
    @OneToOne(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Wallet wallet;

	@JsonIgnore
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

	@JsonIgnore
	@OneToMany(mappedBy="buyer", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SelledProduct> buyers;
	
	@JsonIgnore
	@OneToMany(mappedBy="seller", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SelledProduct> sellers;
	
	
	
	@JsonIgnore
	@OneToMany(mappedBy="buyer", fetch = FetchType.LAZY)
	private List<Participation> participations;
	
	@JsonIgnore
	@OneToMany(mappedBy="buyer", fetch = FetchType.LAZY)
	private List<TraceParticipation> traceParticipations;
	
	


	@Size(max=2500)
	private String file_path;
	

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public String getCustumerId() {
		return custumerId;
	}

	public void setCustumerId(String custumerId) {
		this.custumerId = custumerId;
	}

	public List<SelledProduct> getBuyers() {
		return buyers;
	}

	public void setBuyers(List<SelledProduct> buyers) {
		this.buyers = buyers;
	}

	public List<SelledProduct> getSellers() {
		return sellers;
	}

	public void setSellers(List<SelledProduct> sellers) {
		this.sellers = sellers;
	}

	public List<TraceParticipation> getTraceParticipations() {
		return traceParticipations;
	}

	public void setTraceParticipations(List<TraceParticipation> traceParticipations) {
		this.traceParticipations = traceParticipations;
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

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	@PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

	public Role getRole() {
		return role;
	}

	public void setRoles(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}

//	public List<Auction> getAuctions() {
//		return auctions;
//	}
//
//	public void setAuctions(List<Auction> auctions) {
//		this.auctions = auctions;
//	}

}