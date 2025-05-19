package com.Spring.ecommerce.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int inventory;
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(
			name="category_id",
			referencedColumnName="id"
			)
	private Category category;
	
	@OneToMany(mappedBy ="product",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Image> images;

	public Product(Long id, String name, String brand, BigDecimal price, int inventory, String description,
			Category category, List<Image> images) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.inventory = inventory;
		this.description = description;
		this.category = category;
		this.images = images;
	}

	public Product() {

	}
	
	public Product(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
		super();
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.inventory = inventory;
		this.description = description;
		this.category = category;
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", price=" + price + ", inventory="
				+ inventory + ", description=" + description + ", category=" + category + ", images=" + images + "]";
	}
	
	
}
