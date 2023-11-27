package com.artisana.models;

import java.util.Date;

public class TraceParticipationDTO {

	private Long id;
    private User buyer; 
    private Product product; 
    private double amount;
    private Date createdAt;
    private Date updatedAt;
    
    
    public TraceParticipationDTO(TraceParticipation traceParticipation) {
        this.id = traceParticipation.getId();
        this.amount = traceParticipation.getAmount();
        this.createdAt = traceParticipation.getCreatedAt();
        this.updatedAt = traceParticipation.getUpdatedAt();
        this.buyer = traceParticipation.getBuyer();
        this.product = traceParticipation.getProduct();
    }
    
    public TraceParticipationDTO() {}
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
    
    
    
}
