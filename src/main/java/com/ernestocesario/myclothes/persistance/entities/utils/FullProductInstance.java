package com.ernestocesario.myclothes.persistance.entities.utils;

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
public class FullProductInstance {
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
}
