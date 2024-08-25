package com.ernestocesario.myclothes.configurations.mappers.businessLogic;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.review.ReviewDTO;
import com.ernestocesario.myclothes.persistance.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ReviewMapper {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "stars", source = "stars")
    @Mapping(target = "username", source = "customer.username")
    @Mapping(target = "productDTO", source = "product", qualifiedByName = "toProductDTOFromProduct")
    ReviewDTO toReviewDTO(Review review);
}