package com.ernestocesario.myclothes.persistance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistSharingDTO {
    private String id;
    private String userEmail;

    //this object should be created only for user that has access to a wishlist (i.e. WishlistPermission.SHARED)
}
