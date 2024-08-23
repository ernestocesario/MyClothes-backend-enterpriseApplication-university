package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.DiscountCode;

import java.util.List;

public interface DiscountCodeService {
    List<DiscountCode> getDiscountCodesOfCustomer(Customer customer);
    List<DiscountCode> getAllPublicDiscountCodes();

    boolean addDiscountCodeToCustomer(DiscountCode discountCode, Customer... customer);
    boolean removeDiscountCodeFromCustomer(DiscountCode discountCode, Customer... customer);
}
