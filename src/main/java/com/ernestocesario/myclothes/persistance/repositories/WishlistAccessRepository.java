package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.WishlistAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistAccessRepository extends JpaRepository<WishlistAccess, String> {
}
