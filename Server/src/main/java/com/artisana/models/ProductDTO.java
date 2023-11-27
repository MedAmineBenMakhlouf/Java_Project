package com.artisana.models;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {
	private Long id;
	private String name;
	private String description;
	private Boolean status;
	private double price;
	private String category;
	private Boolean typeProduct;
	private Date startTime;
	private Date endTime;
	private UserDTO user;
	private List<FileDTO> files;
	private List<TraceParticipationDTO> traceParticipations;
	private List<ParticipationDTO> participations;

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.category = product.getCategory();
		this.status = product.getStatus();
		this.startTime = product.getStartTime();
		this.endTime = product.getEndTime();
		this.typeProduct = product.getTypeProduct();
		this.files = product.getFiles().stream().map(FileDTO::new).collect(Collectors.toList());
//		this.traceParticipations = product.getTraceParticipations().stream().map(TraceParticipationDTO::new)
//				.collect(Collectors.toList());
		
		this.participations = product.getParticipations().stream().map(ParticipationDTO::new)
				.collect(Collectors.toList());

		this.user = new UserDTO(product.getUser());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ParticipationDTO> getParticipations() {
		return participations;
	}

	public void setParticipations(List<ParticipationDTO> participations) {
		this.participations = participations;
	}

	public List<TraceParticipationDTO> getTraceParticipations() {
		return traceParticipations;
	}

	public void setTraceParticipations(List<TraceParticipationDTO> traceParticipations) {
		this.traceParticipations = traceParticipations;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public Boolean getTypeProduct() {
		return typeProduct;
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<FileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<FileDTO> files) {
		this.files = files;
	}

}
