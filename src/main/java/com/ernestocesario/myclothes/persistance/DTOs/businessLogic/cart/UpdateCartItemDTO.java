package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCartItemDTO {
    @Positive
    private int quantity;

    @NotBlank
    private String productVariantId;
}
