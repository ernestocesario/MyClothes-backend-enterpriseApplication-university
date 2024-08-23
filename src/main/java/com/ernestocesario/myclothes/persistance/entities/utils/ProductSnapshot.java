package com.ernestocesario.myclothes.persistance.entities.utils;

import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductSnapshot {
    private String brand;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String style;

    @Enumerated(EnumType.STRING)
    private ProductSize size;

    private double price;


    //methods
    public static ProductSnapshot fromProductVariant(ProductVariant productVariant) {
        return new ProductSnapshot(
                productVariant.getProduct().getBrand(),
                productVariant.getProduct().getName(),
                productVariant.getProduct().getDescription(),
                productVariant.getProduct().getCategory(),
                productVariant.getGender(),
                productVariant.getStyle(),
                productVariant.getSize(),
                productVariant.getPrice()
        );
    }
}
