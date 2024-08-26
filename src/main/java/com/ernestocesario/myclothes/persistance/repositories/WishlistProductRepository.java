package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.WishlistProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistProductRepository extends JpaRepository<WishlistProduct, String> {
}
