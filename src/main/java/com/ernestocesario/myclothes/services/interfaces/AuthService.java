package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.users.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(String idTokenString);
}
