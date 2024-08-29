package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.ReviewMapper;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.review.FullReviewDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.review.ReviewDTO;
import com.ernestocesario.myclothes.persistance.entities.Review;
import com.ernestocesario.myclothes.services.implementations.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${reviewsControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewServiceImpl reviewServiceImpl;
    private final ReviewMapper reviewMapper;

    @GetMapping("${productReviewsControllerSubPath}/{productId}")
    public ResponseEntity<Page<ReviewDTO>> getAllReviewsOfProduct(@PathVariable String productId, Pageable pageable) {
        Page<Review> reviewPage = reviewServiceImpl.getAllReviewsOfProduct(productId, pageable);
        return ResponseEntity.ok(reviewPage.map(reviewMapper::toReviewDTO));
    }

    @GetMapping("${customerReviewsControllerSubPath}/{customerId}")
    public Page<FullReviewDTO> getAllReviewsOfCustomer(@PathVariable String customerId, Pageable pageable) {
        Page<Review> reviewPage = reviewServiceImpl.getAllReviewsOfCustomer(customerId, pageable);
        return reviewPage.map(reviewMapper::toFullReviewDTO);
    }

    @GetMapping(params = "keyword")
    public Page<FullReviewDTO> getAllReviewsByKeyword(@RequestParam String keyword, Pageable pageable) {
        Page<Review> reviewPage = reviewServiceImpl.getAllReviewsByKeyword(keyword, pageable);
        return reviewPage.map(reviewMapper::toFullReviewDTO);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<FullReviewDTO> getReviewById(@PathVariable String reviewId) {
        Review review = reviewServiceImpl.getReviewById(reviewId);
        return ResponseEntity.ok(reviewMapper.toFullReviewDTO(review));
    }

    @PostMapping("${productReviewsControllerSubPath}/{productId}")
    public ResponseEntity<Void> addReviewToProduct(@PathVariable String productId, @RequestBody ReviewDTO reviewDTO) {
        Review review = reviewMapper.toReviewFromReviewDTO(reviewDTO);
        reviewServiceImpl.addReviewToProduct(productId, review);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) {
        reviewServiceImpl.removeReviewFromProduct(reviewId);
        return ResponseEntity.ok().build();
    }
}
