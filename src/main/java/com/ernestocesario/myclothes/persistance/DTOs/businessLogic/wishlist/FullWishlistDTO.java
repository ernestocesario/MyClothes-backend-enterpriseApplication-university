package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.FullProductVariantDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class FullWishlistDTO extends WishlistDTO {
    private List<FullProductVariantDTO> products;
}
