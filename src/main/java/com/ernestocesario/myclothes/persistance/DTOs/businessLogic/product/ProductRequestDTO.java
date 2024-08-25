package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDTO {
    private int quantity;
    private FullProductVariantDTO fullProductVariantDTO;
}
