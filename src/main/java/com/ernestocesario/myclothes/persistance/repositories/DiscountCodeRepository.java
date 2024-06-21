package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, String> {
}
