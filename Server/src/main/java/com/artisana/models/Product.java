package com.artisana.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

	public Product() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@NotBlank
	@Size(min = 5, max = 150)
	private String name;

	@NotNull
	@NotBlank
	@Size(min = 5, max = 500)
	private String description;
	
	@NotNull
	@Min(1)
	private double price;
	
	@NotNull
	private int duration;
	//duration must be an enum (selected values bech ma tetmassech bel postman)
	
	private Boolean status;
	
	// Famma 3 type de status fmma sold w unsold
	
	

	@NotNull
	private Boolean typeProduct;
	// Famma max 6 types possibles
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;
	
	//depend b duration 9addech donc nzidou l start date 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;
	
	
	
	@NotNull
	private String category;
	// Famma 6 max category possibles

	// Relationships
	// user can have many product 

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	
	// upload photos(product can have many photos) unique to one product
	@JsonIgnore
	@OneToMany(mappedBy="product", fetch = FetchType.LAZY)
	private List<File> files;
	
	//Many to many users products (ma9souma sur 3 tableau)(participtions users products )
	@JsonIgnore
	@OneToMany(mappedBy="product", fetch = FetchType.LAZY)
	private List<Participation> participations;
	
	
	//Tableau ettabba bih el bid 
	@JsonIgnore
	@OneToMany(mappedBy="product", fetch = FetchType.LAZY)
	private List<TraceParticipation> traceParticipations;
	

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}


	public Boolean getTypeProduct() {
		return typeProduct;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(List<Participation> participations) {
		this.participations = participations;
	}

	public void setTypeProduct(Boolean typeProduct) {
		this.typeProduct = typeProduct;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public List<TraceParticipation> getTraceParticipations() {
		return traceParticipations;
	}

	public void setTarceParticipations(List<TraceParticipation> traceParticipations) {
		this.traceParticipations = traceParticipations;
	}
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setTraceParticipations(List<TraceParticipation> traceParticipations) {
		this.traceParticipations = traceParticipations;
	}

}
