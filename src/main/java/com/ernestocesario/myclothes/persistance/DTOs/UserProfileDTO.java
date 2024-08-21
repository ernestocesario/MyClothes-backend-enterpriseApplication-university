package com.ernestocesario.myclothes.persistance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProfileDTO {
    private String id;
    private String username;
    private String email;
    private String imageUrl;
}
