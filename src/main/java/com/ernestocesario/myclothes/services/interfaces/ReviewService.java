package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Product;
import com.ernestocesario.myclothes.persistance.entities.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviewsOfProduct(String productId);
    Review getReviewById(String reviewId);

    boolean addReviewToProduct(Customer customer, Product product, String reviewContent);
    boolean removeReviewFromProduct(Customer customer, String reviewId);
}
