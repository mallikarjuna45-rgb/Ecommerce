package com.Spring.ecommerce.service.Product;

import java.util.List;

import com.Spring.ecommerce.model.Product;
import com.Spring.ecommerce.requests.AddProductRequest;
import com.Spring.ecommerce.requests.UpdateProductRequest;

public interface ProductServiceI {
		
		Product addProduct(AddProductRequest product);
		
		Product getProductById(Long id);
		
		void deleteProductById(Long id);
		
		Product updateProduct(UpdateProductRequest product,Long id);
		
		List<Product> getAllProducts();
		
		List<Product> getProductsByCategory(String category);
		
		List<Product> getProductsByBrand(String Brand);
		
		List<Product> getProductsByName(String name);
		
		List<Product> getProductsByCategoryAndBrand(String category,String brand);
		
		List<Product> getProductsByCategoryAndName(String category,String name);
		
		List<Product> getProductsByNameAndBrand(String name,String Brand);
		
		Long countProductsByBrandAndName(String brand,String name);
		
		
}
