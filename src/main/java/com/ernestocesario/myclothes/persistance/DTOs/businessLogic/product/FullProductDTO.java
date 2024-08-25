package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class FullProductDTO extends ProductDTO{
    List<ProductVariantDTO> productVariants;
}
