package com.ernestocesario.myclothes.persistance.repositories;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, String> {
    List<DiscountCode> findAllByCustomer(Customer customer);
    DiscountCode findByCustomerAndCodeAndUsedIsFalse(Customer customer, String code);
    boolean existsByCode(String code);
}
