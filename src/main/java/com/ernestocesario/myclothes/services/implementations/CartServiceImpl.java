package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationChecker;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsCustomer;
import com.ernestocesario.myclothes.persistance.entities.Cart;
import com.ernestocesario.myclothes.persistance.entities.CartElement;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.repositories.CartElementRepository;
import com.ernestocesario.myclothes.persistance.repositories.CartRepository;
import com.ernestocesario.myclothes.persistance.repositories.ProductVariantRepository;
import com.ernestocesario.myclothes.services.interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartElementRepository cartElementRepository;
    private final UserServiceImpl userServiceImpl;
    private final IsCustomer isCustomer;

    @Override
    @Transactional
    public Cart getMyCart() {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();
        return customer.getCart();
    }

    @Override
    @Transactional
    public boolean addProductToCart(String productVariantId, int quantity) {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();

        ProductVariant productVariant = productVariantRepository.findById(productVariantId).orElse(null);
        if (productVariant == null)
            return false;

        CartElement cartElement = CartElement.builder()
                .quantity(quantity)
                .productVariant(productVariant)
                .cart(customer.getCart())
                .build();

        cartElementRepository.save(cartElement);
        return true;
    }

    @Override
    @Transactional
    public boolean removeProductFromCart(String productVariantId) {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();
        boolean productRemoved = false;

        Cart cart = customer.getCart();
        Iterator<CartElement> iterator = cart.getCartElements().iterator();
        while (iterator.hasNext()) {
            CartElement cartElement = iterator.next();
            if (cartElement.getProductVariant().getId().equals(productVariantId)) {
                iterator.remove();
                productRemoved = true;
            }
        }

        cartRepository.save(cart);

        return productRemoved;
    }

    @Override
    @Transactional
    public boolean updateProductQuantity(String productVariantId, int quantity) {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();
        boolean productUpdated = false;

        Cart cart = customer.getCart();
        for (CartElement cartElement : cart.getCartElements()) {
            if (cartElement.getProductVariant().getId().equals(productVariantId)) {
                cartElement.setQuantity(quantity);
                productUpdated = true;
            }
        }

        cartRepository.save(cart);
        return true;
    }
}
