package com.ernestocesario.myclothes.persistance.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "wishlists")
@NoArgsConstructor
@Data
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;



    //associations
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wishlist", cascade = CascadeType.ALL)
    private List<WishlistAccess> wishlistAccesses;

    @ManyToMany
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_variant_id")
    )
    private List<ProductVariant> productVariants;
}
