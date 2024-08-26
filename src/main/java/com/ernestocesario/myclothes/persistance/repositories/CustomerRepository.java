package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Page<Customer> findAllByBannedIsTrue(Pageable pageable);
    Page<Customer> findAllByUsernameContainingIgnoreCase(String keyword, Pageable pageable);
    Customer findByEmail(String email);
}
