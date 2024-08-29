package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistShareDTO {
    private String id;

    @NotBlank
    private String wishlistId;

    @NotBlank
    @Email
    private String userEmail;
}
