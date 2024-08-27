package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import com.ernestocesario.myclothes.configurations.constants.product.ProductConstants;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class FullProductVariantDTO extends ProductVariantDTO {
    @NotBlank
    @Size(min = ProductConstants.MIN_BRAND_NAME_LENGTH, max = ProductConstants.MAX_BRAND_NAME_LENGTH)
    private String brand;

    @NotBlank
    @Size(min = ProductConstants.MIN_PRODUCT_NAME_LENGTH, max = ProductConstants.MAX_PRODUCT_NAME_LENGTH)
    private String name;

    @NotBlank
    @Size(min = ProductConstants.MIN_PRODUCT_DESCRIPTION_LENGTH, max = ProductConstants.MAX_PRODUCT_DESCRIPTION_LENGTH)
    private String description;

    @NotNull
    private ProductCategory category;
}