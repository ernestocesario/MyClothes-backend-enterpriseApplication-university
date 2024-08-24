package com.ernestocesario.myclothes.persistance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscountCodeDTO {
    private String id;
    private String code;
    private int discountPercentage;
    private boolean used;
}