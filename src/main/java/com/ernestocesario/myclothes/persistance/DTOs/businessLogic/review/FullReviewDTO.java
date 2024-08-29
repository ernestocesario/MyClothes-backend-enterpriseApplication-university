package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.review;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.ProductDTO;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class FullReviewDTO extends ReviewDTO {
    private ProductDTO productDTO;
}
