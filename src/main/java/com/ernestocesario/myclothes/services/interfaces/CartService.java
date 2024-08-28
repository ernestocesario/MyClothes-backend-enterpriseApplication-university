package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Cart;

public interface CartService {
    Cart getMyCart();

    boolean addProductToCart(String productVariantId, int quantity);
    boolean removeProductFromCart(String productVariantId);
    boolean updateProductQuantity(String productVariantId, int quantity);
}