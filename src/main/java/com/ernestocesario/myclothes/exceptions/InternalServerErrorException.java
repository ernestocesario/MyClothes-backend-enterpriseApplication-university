package com.ernestocesario.myclothes.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
