package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
