package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    Page<Review> getAllReviewsOfProduct(String productId, Pageable pageable);
    Page<Review> getAllReviewsOfCustomer(String customerId, Pageable pageable);
    Page<Review> getAllReviewsByKeyword(String keyword, Pageable pageable);  //only admin
    Review getReviewById(String reviewId);

    boolean addReviewToProduct(String productId, Review review);  //only customer
    boolean removeReviewFromProduct(String reviewId);
}