package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Product;
import com.ernestocesario.myclothes.persistance.entities.Review;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.repositories.CustomerRepository;
import com.ernestocesario.myclothes.persistance.repositories.ProductRepository;
import com.ernestocesario.myclothes.persistance.repositories.ReviewRepository;
import com.ernestocesario.myclothes.services.interfaces.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public Page<Review> getAllReviewsOfProduct(String productId, Pageable pageable) {
        Product product = productRepository.findById(productId).orElseThrow(InternalServerErrorException::new);

        Sort sort = Sort.by(Sort.Direction.DESC, "stars");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return reviewRepository.findAllByProduct(product, pageable);
    }

    @Override
    public Page<Review> getAllReviewsOfCustomer(String customerId, Pageable pageable) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(InternalServerErrorException::new);

        Sort sort = Sort.by(Sort.Direction.DESC, "stars");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return reviewRepository.findAllByCustomer(customer, pageable);
    }

    @Override
    public Review getReviewById(String reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(InternalServerErrorException::new);
    }

    @Override
    public boolean addReviewToProduct(String productId, Review review) {
        User user = userServiceImpl.getCurrentUser();
        if (!user.isCustomer())
            throw new InternalServerErrorException();

        Customer customer = (Customer) user;

        Product product = productRepository.findById(productId).orElseThrow(InternalServerErrorException::new);

        review.setCustomer(customer);
        review.setProduct(product);

        reviewRepository.save(review);

        return true;
    }

    @Override
    public boolean removeReviewFromProduct(String reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(InternalServerErrorException::new);
        reviewRepository.delete(review);

        return true;
    }
}