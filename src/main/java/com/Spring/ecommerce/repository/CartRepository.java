package com.Spring.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Spring.ecommerce.model.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

}
