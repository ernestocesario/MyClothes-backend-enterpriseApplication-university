package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.CartMapper;
import com.ernestocesario.myclothes.exceptions.InvalidInputException;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.cart.CartDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.cart.UpdateCartItemDTO;
import com.ernestocesario.myclothes.persistance.entities.Cart;
import com.ernestocesario.myclothes.services.implementations.CartServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<Void> addProductToCart(@Valid @RequestBody UpdateCartItemDTO updateCartItemDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new InvalidInputException();

        String productVariantId = updateCartItemDTO.getProductVariantId();
        int quantity = updateCartItemDTO.getQuantity();

        cartServiceImpl.addProductToCart(productVariantId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("${itemsCartControllerSubPath}/{productVariantId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable String productVariantId) {
        cartServiceImpl.removeProductFromCart(productVariantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("${itemsCartControllerSubPath}")
    public ResponseEntity<Void> updateProductQuantity(@Valid @RequestBody UpdateCartItemDTO updateCartItemDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new InvalidInputException();

        String productVariantId = updateCartItemDTO.getProductVariantId();
        int quantity = updateCartItemDTO.getQuantity();

        cartServiceImpl.updateProductQuantity(productVariantId, quantity);
        return ResponseEntity.ok().build();
    }
}