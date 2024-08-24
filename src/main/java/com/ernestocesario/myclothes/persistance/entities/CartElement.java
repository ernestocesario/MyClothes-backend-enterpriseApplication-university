package com.ernestocesario.myclothes.persistance.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_elements")
@NoArgsConstructor
@Data
public class CartElement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "quantity", nullable = false)
    @Positive
    private int quantity;


    //Builder
    @Builder
    public CartElement(int quantity, ProductVariant productVariant, Cart cart) {
        this.quantity = quantity;
        this.productVariant = productVariant;
        this.cart = cart;
    }


    //associations
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
}
