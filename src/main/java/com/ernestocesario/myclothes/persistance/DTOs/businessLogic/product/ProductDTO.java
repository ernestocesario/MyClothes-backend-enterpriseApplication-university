package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import com.ernestocesario.myclothes.persistance.entities.utils.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    @NotBlank
    private String productId;

    private String brand;
    private String name;
    private String description;
    private ProductCategory category;
}
