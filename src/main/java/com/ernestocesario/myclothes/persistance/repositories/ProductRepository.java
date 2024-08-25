package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Product;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findAllByCategory(ProductCategory productCategory, Pageable pageable);
    boolean existsByBrandAndName(String brand, String name);
}
