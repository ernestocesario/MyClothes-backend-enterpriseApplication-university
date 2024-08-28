package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.CartMapper;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.cart.CartDTO;
import com.ernestocesario.myclothes.persistance.entities.Cart;
import com.ernestocesario.myclothes.services.implementations.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${cartControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartServiceImpl;
    private final CartMapper cartMapper;


    @GetMapping("${itemsCartControllerSubPath}")
    public ResponseEntity<CartDTO> getCart() {
        Cart cart = cartServiceImpl.getMyCart();
        return ResponseEntity.ok(cartMapper.toCartDTO(cart));
    }

    @PostMapping("${itemsCartControllerSubPath}")
    public ResponseEntity<Void> addProductToCart(String productVariantId, int quantity) {
        cartServiceImpl.addProductToCart(productVariantId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("${itemsCartControllerSubPath}")
    public ResponseEntity<Void> removeProductFromCart(String productVariantId) {
        cartServiceImpl.removeProductFromCart(productVariantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("${itemsCartControllerSubPath}")
    public ResponseEntity<Void> updateProductQuantity(String productVariantId, int quantity) {
        cartServiceImpl.updateProductQuantity(productVariantId, quantity);
        return ResponseEntity.ok().build();
    }
}
