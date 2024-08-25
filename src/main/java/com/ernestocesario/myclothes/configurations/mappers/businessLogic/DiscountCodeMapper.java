package com.ernestocesario.myclothes.configurations.mappers.businessLogic;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.discountCode.DiscountCodeDTO;
import com.ernestocesario.myclothes.persistance.entities.DiscountCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscountCodeMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    @Mapping(target = "discountPercentage", source = "discountPercentage")
    @Mapping(target = "used", source = "used")
    DiscountCodeDTO toDiscountCodeDTO(DiscountCode discountCode);
}