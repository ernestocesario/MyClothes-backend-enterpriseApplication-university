package com.ernestocesario.myclothes.persistance.DTOs;

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
