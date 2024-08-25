package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.cart;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.ProductRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {
    List<ProductRequestDTO> productRequestDTOList;
}
