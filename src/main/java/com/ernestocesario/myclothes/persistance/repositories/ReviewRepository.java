package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
