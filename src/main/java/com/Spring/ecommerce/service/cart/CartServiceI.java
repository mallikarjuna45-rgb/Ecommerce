package com.Spring.ecommerce.service.cart;

import java.math.BigDecimal;

import com.Spring.ecommerce.model.Cart;

public interface CartServiceI {
		
	public Cart getCartById(Long id);
	public void clearCart(Long id);
	public BigDecimal getTotalPrice(Long Id);
	Long initializeNewCart();
}
