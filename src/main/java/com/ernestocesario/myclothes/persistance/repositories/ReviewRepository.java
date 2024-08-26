package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Product;
import com.ernestocesario.myclothes.persistance.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Page<Review> findAllByProduct(Product product, Pageable pageable);
    Page<Review> findAllByCustomer(Customer customer, Pageable pageable);
    Page<Review> findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String keyword1, String keyword2, Pageable pageable);
}
