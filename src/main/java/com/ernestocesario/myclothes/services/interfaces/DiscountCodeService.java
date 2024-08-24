package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscountCodeService {
    Page<DiscountCode> getDiscountCodesOfCustomer(Pageable pageable);

    boolean addDiscountCodeToCustomer(int discountCodePercentage, String customerEmail);
    boolean removeDiscountCodeById(String discountCodeId);
}
