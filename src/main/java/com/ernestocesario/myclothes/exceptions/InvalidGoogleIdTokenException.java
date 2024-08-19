package com.ernestocesario.myclothes.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidGoogleIdTokenException extends RuntimeException {
    public InvalidGoogleIdTokenException(String message) {
        super(message);
    }
}
