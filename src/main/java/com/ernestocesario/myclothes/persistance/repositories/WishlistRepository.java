package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, String> {
    Page<Wishlist> findAllByPubIsTrue(Pageable pageable);
    Page<Wishlist> findAllByPubIsTrueAndNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Wishlist> findAllByCustomer(Customer customer, Pageable pageable);
    Page<Wishlist> findByWishlistSharesCustomer(Customer customer, Pageable pageable);
    boolean existsByIdAndCustomer(String id, Customer customer);
}
