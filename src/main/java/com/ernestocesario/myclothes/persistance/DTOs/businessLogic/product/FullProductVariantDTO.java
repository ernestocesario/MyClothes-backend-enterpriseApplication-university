package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import com.ernestocesario.myclothes.persistance.entities.utils.ProductCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class FullProductVariantDTO extends ProductVariantDTO {
    private String brand;
    private String name;
    private String description;
    private ProductCategory category;
}