package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.review;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.FullProductVariantDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.ProductDTO;
import com.ernestocesario.myclothes.persistance.entities.utils.ReviewStars;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDTO {
    private String id;
    private String title;
    private String content;
    private ReviewStars stars;
    private String username;
    private ProductDTO productDTO;
}
