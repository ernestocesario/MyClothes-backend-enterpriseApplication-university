package com.ernestocesario.myclothes.configurations.mappers;

import com.ernestocesario.myclothes.persistance.DTOs.DiscountCodeDTO;
import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscountCodeMapper {

    @Mapping(target = "code", source = "code")
    @Mapping(target = "discountPercentage", source = "discountPercentage")
    @Mapping(target = "used", source = "used")
    DiscountCodeDTO toDiscountCodeDTO(DiscountCode discountCode);
}