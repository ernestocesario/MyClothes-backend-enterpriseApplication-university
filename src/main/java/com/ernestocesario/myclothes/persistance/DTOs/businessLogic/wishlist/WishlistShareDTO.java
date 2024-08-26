package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistShareDTO {
    private String id;
    private String wishlistId;
    private String userEmail;
}
