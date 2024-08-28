package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.DiscountCode;

import java.util.List;

public interface DiscountCodeService {
    List<DiscountCode> getMyDiscountCodes();

    boolean addDiscountCodeToCustomer(int discountCodePercentage, String customerEmail, boolean isSystem);
    boolean removeDiscountCodeById(String discountCodeId, boolean isSystem);
}
