package com.ernestocesario.myclothes.persistance.DTOs.businessLogic.review;

import com.ernestocesario.myclothes.configurations.constants.product.ReviewConstants;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.FullProductVariantDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.ProductDTO;
import com.ernestocesario.myclothes.persistance.entities.utils.ReviewStars;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDTO {
    private String id;

    @NotBlank
    @Size(min = ReviewConstants.MIN_REVIEW_TITLE_LENGTH, max = ReviewConstants.MAX_REVIEW_TITLE_LENGTH)
    private String title;

    @NotBlank
    @Size(min = ReviewConstants.MIN_REVIEW_CONTENT_LENGTH, max = ReviewConstants.MAX_REVIEW_CONTENT_LENGTH)
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReviewStars stars;

    private String username;

    @Valid
    private ProductDTO productDTO;
}
