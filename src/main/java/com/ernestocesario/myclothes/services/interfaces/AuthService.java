package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.auth.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(String idTokenString);
    Boolean logout();
}
