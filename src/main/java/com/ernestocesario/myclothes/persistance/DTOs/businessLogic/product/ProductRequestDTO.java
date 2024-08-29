package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDTO {
    private int quantity;
    private FullProductVariantDTO fullProductVariantDTO;
}
