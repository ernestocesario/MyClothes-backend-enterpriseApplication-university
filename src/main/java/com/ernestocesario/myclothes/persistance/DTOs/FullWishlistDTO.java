package com.ernestocesario.myclothes.persistance.DTOs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FullWishlistDTO extends WishlistDTO {
    private List<ProductDTO> products;

    @Override
    public String toString() {
        return "FullWishlistDTO{" +
                "id='" + getId() + '\'' + ", " +
                "name='" + getName() + '\'' + ", " +
                "products=" + products + '}';
    }
}
