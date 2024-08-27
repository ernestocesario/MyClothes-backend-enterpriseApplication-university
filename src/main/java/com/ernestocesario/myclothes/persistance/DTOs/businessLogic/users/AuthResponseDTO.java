package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponseDTO {
    @NotBlank
    private String AccessToken;

    @NotBlank
    private String RefreshToken;
}
