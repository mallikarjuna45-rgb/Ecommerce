package com.Spring.ecommerce.service.cartItem;

public interface CartItemServiceI {
	public void addItemToCart(Long cartId,Long productId,int Quantity);
	public void removeItemFromCart(Long cartId,Long producId);
	public void updateItemQuantity(Long cartId,Long productId,int quanity);
	

}
