package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import com.ernestocesario.myclothes.persistance.entities.utils.Gender;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductSize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductVariantDTO {
    private String productId;
    private String productVariantId;
    private Gender gender;
    private String style;
    private ProductSize size;
    private double price;
}
