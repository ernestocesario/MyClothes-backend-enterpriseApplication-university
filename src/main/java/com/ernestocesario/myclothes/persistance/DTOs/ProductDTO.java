package com.ernestocesario.myclothes.persistance.DTOs;

import com.ernestocesario.myclothes.persistance.entities.utils.Gender;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductCategory;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductSize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private String productId;
    private String productVariantId;
    private String brand;
    private String name;
    private String description;
    private ProductCategory category;
    private Gender gender;
    private String style;
    private ProductSize size;
    private double price;
}
