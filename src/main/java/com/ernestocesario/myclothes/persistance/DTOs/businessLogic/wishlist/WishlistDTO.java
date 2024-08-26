package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistDTO {
    private String id;
    private String name;
    private boolean pub;
}
