package com.ernestocesario.myclothes.persistance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {
    List<ProductRequestDTO> products;
}
