package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import com.ernestocesario.myclothes.persistance.entities.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistProductRepository extends JpaRepository<WishlistProduct, String> {
    Optional<WishlistProduct> findByWishlistAndProductVariant(Wishlist wishlist, ProductVariant productVariant);
}
