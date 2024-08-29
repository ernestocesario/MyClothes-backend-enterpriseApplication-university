package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist;

import com.ernestocesario.myclothes.configurations.constants.wishlist.WishlistConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

@Data
@NoArgsConstructor
public class WishlistCreationDTO {
    @NotNull
    @Size(min = WishlistConstants.MIN_WISHLIST_NAME_LENGTH, max = WishlistConstants.MAX_WISHLIST_NAME_LENGTH)
    private String name;
}
