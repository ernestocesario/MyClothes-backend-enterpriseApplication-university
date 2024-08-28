package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import com.ernestocesario.myclothes.configurations.constants.product.ProductConstants;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    @NotBlank
    private String productId;

    @NotNull
    @Size(min = ProductConstants.MIN_BRAND_NAME_LENGTH, max = ProductConstants.MAX_BRAND_NAME_LENGTH)
    private String brand;

    @NotNull
    @Size(min = ProductConstants.MIN_PRODUCT_NAME_LENGTH, max = ProductConstants.MAX_PRODUCT_NAME_LENGTH)
    private String name;

    @NotNull
    @Size(min = ProductConstants.MIN_PRODUCT_DESCRIPTION_LENGTH, max = ProductConstants.MAX_PRODUCT_DESCRIPTION_LENGTH)
    private String description;

    @NotNull
    private ProductCategory category;
}
