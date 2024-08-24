package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, String> {
    Page<DiscountCode> findAllByCustomer(Customer customer, Pageable pageable);
    Page<DiscountCode> findAllByCustomerIsNull(Pageable pageable);
    DiscountCode findByCustomerAndCodeAndUsedIsFalse(Customer customer, String code);
    boolean existsByCode(String code);
}
