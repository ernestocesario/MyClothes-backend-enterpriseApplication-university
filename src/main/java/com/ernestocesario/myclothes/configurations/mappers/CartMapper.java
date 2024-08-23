package com.ernestocesario.myclothes.configurations.mappers;

import com.ernestocesario.myclothes.persistance.DTOs.CartDTO;
import com.ernestocesario.myclothes.persistance.DTOs.ProductRequestDTO;
import com.ernestocesario.myclothes.persistance.entities.Cart;
import com.ernestocesario.myclothes.persistance.entities.CartElement;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartMapper {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "productRequestDTOList", source = "cartElements", qualifiedByName = "toProductRequestDTOList")
    CartDTO toCartDTO(Cart cart);


    //Utility methods
    @Named("toProductRequestDTOList")
    @IterableMapping(qualifiedByName = "toProductRequestDTOFromCartElement")
    List<ProductRequestDTO> toProductRequestDTOList(List<CartElement> cartElements);
}
