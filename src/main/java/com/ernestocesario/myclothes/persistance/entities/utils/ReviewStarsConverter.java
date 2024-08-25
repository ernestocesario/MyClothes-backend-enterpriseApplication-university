package com.ernestocesario.myclothes.persistance.entities.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ReviewStarsConverter implements AttributeConverter<ReviewStars, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReviewStars attribute) {
        if (attribute == null)
            return null;

        return attribute.getValue();
    }

    @Override
    public ReviewStars convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        return ReviewStars.fromValue(dbData);
    }
}
