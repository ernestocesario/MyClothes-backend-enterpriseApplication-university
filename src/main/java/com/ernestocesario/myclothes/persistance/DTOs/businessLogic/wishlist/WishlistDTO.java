package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist;

import com.ernestocesario.myclothes.configurations.constants.product.WishlistConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistDTO {
    private String id;

    @NotBlank
    @Size(min = WishlistConstants.MIN_WISHLIST_NAME_LENGTH, max = WishlistConstants.MAX_WISHLIST_NAME_LENGTH)
    private String name;

    private boolean pub;
}
