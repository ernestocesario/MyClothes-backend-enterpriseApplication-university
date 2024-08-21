package com.ernestocesario.myclothes.persistance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDTO {
    private int quantity;
    private ProductDTO productDTO;
}
