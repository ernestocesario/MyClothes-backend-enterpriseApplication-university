package com.ernestocesario.myclothes.persistance.entities;

import com.ernestocesario.myclothes.persistance.entities.utils.Gender;
import com.ernestocesario.myclothes.persistance.entities.utils.OrderProduct;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductSize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "product_variants")
@NoArgsConstructor
@Data
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "style", nullable = false)  //it can be anything, from color to shape, ... but not size
    private String style;

    @Column(name = "size", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductSize size;

    @Column(name = "stock", nullable = false)
    @PositiveOrZero
    private int stock;

    @Column(name = "price", nullable = false)
    @Positive
    private double price;



    //associations
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant", cascade = CascadeType.ALL)
    private List<ProductPicture> pictures;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @ManyToMany(mappedBy = "productVariants")
    private List<Wishlist> wishlists;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productVariant", cascade = CascadeType.ALL)
    private List<CartElement> cartElements;
}
