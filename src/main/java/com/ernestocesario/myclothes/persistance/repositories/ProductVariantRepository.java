package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.entities.utils.Gender;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, String> {
    boolean existsByGenderAndStyleAndSize(Gender gender, String style, ProductSize productSize);
}
