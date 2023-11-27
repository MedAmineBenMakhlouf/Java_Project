package com.artisana.models;

import java.util.Date;

public class FileDTO {

    private Long id;
    private String path;
    private ProductDTO product; // We only store the product ID instead of the whole product
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public FileDTO() {}

    public FileDTO(File file) {
        this.id = file.getId();
        this.path = file.getPath();
//        this.product = new ProductDTO(file.getProduct());
        this.createdAt = file.getCreatedAt();
        this.updatedAt = file.getUpdatedAt();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



    public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
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
