package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.users;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponseDTO {
    private String AccessToken;
    private String RefreshToken;
}
