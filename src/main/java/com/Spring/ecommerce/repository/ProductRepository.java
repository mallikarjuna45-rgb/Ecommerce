package com.Spring.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Spring.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

	List<Product> findAllByCategoryName(String category);

	List<Product> findAllByBrand(String brand);

	List<Product> findAllByName(String name);

	List<Product> findAllByCategoryNameAndName(String category, String name);

	List<Product> findAllByCategoryNameAndBrand(String category, String brand);

	List<Product> findAllByNameAndBrand(String name, String brand);

	Long countByBrandAndName(String brand,String name);

}
