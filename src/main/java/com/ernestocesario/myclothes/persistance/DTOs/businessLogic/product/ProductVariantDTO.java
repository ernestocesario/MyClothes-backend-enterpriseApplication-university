package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import com.ernestocesario.myclothes.configurations.constants.product.ProductConstants;
import com.ernestocesario.myclothes.persistance.entities.utils.Gender;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductSize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductVariantDTO {
    private String productId;
    private String productVariantId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Size(min = ProductConstants.MIN_PRODUCT_VARIANT_STYLE_LENGTH, max = ProductConstants.MAX_PRODUCT_VARIANT_STYLE_LENGTH)
    private String style;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductSize size;

    @PositiveOrZero
    private int stock;

    @Positive
    private double price;
}
