package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import com.ernestocesario.myclothes.persistance.entities.WishlistShare;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistShareRepository extends JpaRepository<WishlistShare, String> {
    boolean existsWishlistShareByWishlistAndCustomer(Wishlist wishlist, Customer customer);
    Optional<WishlistShare> findByWishlistAndCustomer_Email(Wishlist wishlist, String customerRecipientEmail);
}
