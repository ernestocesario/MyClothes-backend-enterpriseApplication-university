package com.ernestocesario.myclothes.configurations.mappers.businessLogic;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.review.FullReviewDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.review.ReviewDTO;
import com.ernestocesario.myclothes.persistance.entities.Review;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ReviewMapper {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    /* Entity to DTO */
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "stars", source = "stars")
    @Mapping(target = "username", source = "customer.username")
    ReviewDTO toReviewDTO(Review review);

    default FullReviewDTO toFullReviewDTO(Review review) {
        ReviewDTO reviewDTO = toReviewDTO(review);
        FullReviewDTO fullReviewDTO = new FullReviewDTO();
        BeanUtils.copyProperties(reviewDTO, fullReviewDTO);

        fullReviewDTO.setProductDTO(productMapper.toProductDTO(review.getProduct()));
        return fullReviewDTO;
    }



    /* DTO to Entity */
    @Mapping(target = "id", constant = "null")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "stars", source = "stars")
    Review toReviewFromReviewDTO(ReviewDTO reviewDTO);
}