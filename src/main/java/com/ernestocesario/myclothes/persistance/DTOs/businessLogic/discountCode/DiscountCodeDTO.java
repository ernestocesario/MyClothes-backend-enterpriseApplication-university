package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.discountCode;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscountCodeDTO {
    private String id;
    private String code;

    @Min(1)
    @Max(99)
    private int discountPercentage;
    private boolean used;
}