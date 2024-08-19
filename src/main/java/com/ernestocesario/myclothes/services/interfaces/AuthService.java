package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.DTOs.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(String idTokenString);
}
