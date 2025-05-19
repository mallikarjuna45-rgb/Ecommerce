package com.Spring.ecommerce.requests;

import java.math.BigDecimal;

import com.Spring.ecommerce.model.Category;

public class UpdateProductRequest {
		
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;
	private Category category;
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
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public UpdateProductRequest(Long id, String name, String brand, BigDecimal price, int inventory, String description,
			Category category) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.inventory = inventory;
		this.description = description;
		this.category = category;
	}
	public UpdateProductRequest() {

	}
	@Override
	public String toString() {
		return "AddProductRequest [id=" + id + ", name=" + name + ", brand=" + brand + ", price=" + price
				+ ", inventory=" + inventory + ", description=" + description + ", category=" + category + "]";
	}
}
