package com.ernestocesario.myclothes.persistance.entities.utils;

import lombok.Getter;

@Getter
public enum ReviewStars {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int value;

    ReviewStars(int value) {
        this.value = value;
    }



    //public methods
    public static ReviewStars fromValue(int value) {
        for (ReviewStars reviewStars : ReviewStars.values()) {
            if (reviewStars.value == value) {
                return reviewStars;
            }
        }
        return null;
    }
}
