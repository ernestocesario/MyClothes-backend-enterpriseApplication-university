package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscountCodeService {
    Page<DiscountCode> getMyDiscountCodes(Pageable pageable);

    boolean addDiscountCodeToCustomer(int discountCodePercentage, String customerEmail, boolean isSystem);
    boolean removeDiscountCodeById(String discountCodeId, boolean isSystem);
}
