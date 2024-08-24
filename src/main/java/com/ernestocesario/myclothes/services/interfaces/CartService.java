package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Cart;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import org.springframework.transaction.annotation.Transactional;

public interface CartService {
    Cart getMyCart();

    boolean addProductToCart(String productVariantId, int quantity);
    boolean removeProductFromCart(String productVariantId);
    boolean updateProductInCart(String productVariantId, int quantity);
}