package com.bookstore.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bookstore.exception.CartNotFoundException;
import com.bookstore.model.Cart;
import com.bookstore.model.CartItem;

public class CartRepository {
    private static final Map<Long, Cart> carts = new HashMap<>();

    public Cart getCartForCustomer(Long customerId) {
        Cart cart = carts.get(customerId);
        if (cart == null) {
            // Create a new cart if one doesn't exist
            cart = new Cart();
            cart.setCustomerId(customerId);
            cart.setItems(new ArrayList<>());
            carts.put(customerId, cart);
        }
        return cart;
    }

    public Cart updateCart(Cart cart) {
        carts.put(cart.getCustomerId(), cart);
        return cart;
    }

    public void addItemToCart(Long customerId, CartItem item) {
        // Validate item
        if (item == null || item.getBookId() == null || item.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid cart item");
        }
        Cart cart = getCartForCustomer(customerId);
        
        boolean itemExists = false;
        for (CartItem existingItem : cart.getItems()) {
            if (existingItem.getBookId().equals(item.getBookId())) {
                // Increase quantity if item already exists
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                itemExists = true;
                break;
            }
        }
        
        // Add new item if it doesn't exist
        if (!itemExists) {
            cart.getItems().add(item);
        }
        
        updateCart(cart);
    }

    public void updateCartItem(Long customerId, Long bookId, int quantity) {
        Cart cart = getCartForCustomer(customerId);
        boolean itemFound = false;
        
        for (CartItem item : cart.getItems()) {
            if (item.getBookId().equals(bookId)) {
                item.setQuantity(quantity);
                itemFound = true;
                break;
            }
        }
        
        if (!itemFound) {
            // Add a new item if it doesn't exist in the cart
            CartItem newItem = new CartItem(bookId, quantity);
            cart.getItems().add(newItem);
        }
        
        // Save the updated cart
        updateCart(cart);
        
    }

    public void removeItemFromCart(Long customerId, Long bookId) {
        Cart cart = getCartForCustomer(customerId);
        
        boolean removed = cart.getItems().removeIf(item -> item.getBookId().equals(bookId));

        if(!removed) {
            throw new CartNotFoundException("Item with bookId " + bookId + " not found in cart for customerId " + customerId);
        }
        updateCart(cart);
    }
    
    public void clearCart(Long customerId) {
        Cart cart = getCartForCustomer(customerId);
        cart.getItems().clear();
        updateCart(cart);
    }
}
