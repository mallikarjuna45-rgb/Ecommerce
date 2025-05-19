package com.Spring.ecommerce.service.cartItem;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.Spring.ecommerce.exceptions.ResourceNotFoundException;
import com.Spring.ecommerce.model.Cart;
import com.Spring.ecommerce.model.CartItem;
import com.Spring.ecommerce.model.Product;
import com.Spring.ecommerce.repository.CartItemRepository;
import com.Spring.ecommerce.repository.CartRepository;
import com.Spring.ecommerce.service.Product.ProductService;
import com.Spring.ecommerce.service.cart.CartService;
@Service
public class CartItemService implements CartItemServiceI {
	
	private final CartService cartService;
	private final ProductService productService;
	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	

	

	public CartItemService(CartService cartService, ProductService productService,
			CartItemRepository cartItemRepository, CartRepository cartRepository) {
		this.cartService = cartService;
		this.productService = productService;
		this.cartItemRepository = cartItemRepository;
		this.cartRepository = cartRepository;
	}

	@Override
	public void addItemToCart(Long cartId, Long productId, int quantity) {
			Product product = productService.getProductById(productId);
			Cart cart = cartService.getCartById(cartId);
			CartItem cartItem = cart.getItems().stream()
					.filter(item->item.getProduct().getId().equals(productId))
					.findFirst().orElse(new CartItem());
			if(cartItem.getId() == null) {
				cartItem.setCart(cart);
				cartItem.setProduct(product);
				cartItem.setUnitPrice(product.getPrice());
				cartItem.setQuantity(quantity);
			}
			else {
				cartItem.setQuantity(cartItem.getQuantity()+quantity);
			}
			cartItem.setTotalPrice();
			cart.addItem(cartItem);
			cartItemRepository.save(cartItem);
			cartRepository.save(cart);
	}

	@Override
	public void removeItemFromCart(Long cartId, Long productId) {
		 Cart cart = cartService.getCartById(cartId);
	        CartItem itemToRemove = getCartItem(cartId, productId);
	        cart.removeItem(itemToRemove);
	        cartRepository.save(cart);
	        cartItemRepository.delete(itemToRemove);
		
	}

	@Override
	public void updateItemQuantity(Long cartId, Long productId, int quantity) {
		Cart cart = cartService.getCartById(cartId);
	    cart.getItems()
	            .stream()
	            .filter(item -> item.getProduct().getId().equals(productId))
	            .findFirst()
	            .ifPresent(item -> {
	                if (quantity == 0) {
	                    cart.removeItem(item);
	                    cartItemRepository.delete(item); // Delete if quantity is 0
	                } else {
	                    item.setQuantity(quantity);
	                    item.setUnitPrice(item.getProduct().getPrice());
	                    item.setTotalPrice();
	                }
	            });
	    BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
	}
	public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCartById(cartId);
        return  cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

}
