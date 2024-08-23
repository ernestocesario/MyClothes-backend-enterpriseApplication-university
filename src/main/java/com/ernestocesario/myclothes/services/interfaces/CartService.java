package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Cart;
import com.ernestocesario.myclothes.persistance.entities.Customer;

public interface CartService {
    Cart getCartByCustomer(Customer customer);

    boolean addProductToCart(Customer customer, String productVariantId, int quantity);
    boolean removeProductFromCart(Customer customer, String productVariantId);
    boolean updateCart(Customer customer, Cart cart);
}