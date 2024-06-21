package com.ernestocesario.myclothes.persistance.entities.utils;

import com.ernestocesario.myclothes.persistance.entities.Order;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_products")
@NoArgsConstructor
@Data
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "quantity", nullable = false)
    @Positive
    private int quantity;

    @AttributeOverrides({
            @AttributeOverride(name = "brand", column = @Column(nullable = false)),
            @AttributeOverride(name = "name", column = @Column(nullable = false)),
            @AttributeOverride(name = "description", column = @Column(nullable = false)),
            @AttributeOverride(name = "category", column = @Column(nullable = false)),
            @AttributeOverride(name = "gender", column = @Column(nullable = false)),
            @AttributeOverride(name = "style", column = @Column(nullable = false)),
            @AttributeOverride(name = "size", column = @Column(nullable = false)),
            @AttributeOverride(name = "price", column = @Column(nullable = false)),
    })
    private FullProductInstance fullProductInstance;



    //associations
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;
}
