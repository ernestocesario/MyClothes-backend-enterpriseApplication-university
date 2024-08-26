package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.WishlistShare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistShareRepository extends JpaRepository<WishlistShare, String> {
}
