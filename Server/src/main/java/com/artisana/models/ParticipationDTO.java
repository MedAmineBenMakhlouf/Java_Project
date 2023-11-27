package com.artisana.models;

public class ParticipationDTO {
	private Long id;
	private User buyer;
	private Product product;
	private double amount;
	
	public ParticipationDTO(Participation participation) {
		this.id = participation.getId();
		this.buyer = participation.getBuyer();
		this.product = participation.getProduct();
		this.amount = participation.getAmount();
	}
	
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
	
	
}
