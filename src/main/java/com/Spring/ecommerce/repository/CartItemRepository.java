package com.Spring.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Spring.ecommerce.model.CartItem;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

	void deleteAllByCartId(Long id);

}
