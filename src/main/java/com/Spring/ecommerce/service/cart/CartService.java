package com.Spring.ecommerce.service.cart;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.Spring.ecommerce.exceptions.ResourceNotFoundException;
import com.Spring.ecommerce.model.Cart;
import com.Spring.ecommerce.repository.CartItemRepository;
import com.Spring.ecommerce.repository.CartRepository;

@Service
public class CartService implements CartServiceI {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
   // private final AtomicLong cartIdGenerator = new AtomicLong(0);
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cart Not Found"));
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCartById(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO); // Reset total amount instead of deleting the cart
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return getCartById(id).getTotalAmount();
    }
    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
//        Long newCartId = cartIdGenerator.incrementAndGet();
//        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();

    }
}

