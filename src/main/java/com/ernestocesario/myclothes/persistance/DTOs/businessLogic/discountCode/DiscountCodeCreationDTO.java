package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.discountCode;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DiscountCodeCreationDTO {
    @Min(1)
    @Max(99)
    private int discountPercentage;

    @Email
    private String customerEmail;
}
