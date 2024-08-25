package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.FullProductVariantDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FullWishlistDTO extends WishlistDTO {
    private List<FullProductVariantDTO> products;

    @Override
    public String toString() {
        return "FullWishlistDTO{" +
                "id='" + getId() + '\'' + ", " +
                "name='" + getName() + '\'' + ", " +
                "products=" + products + '}';
    }
}
