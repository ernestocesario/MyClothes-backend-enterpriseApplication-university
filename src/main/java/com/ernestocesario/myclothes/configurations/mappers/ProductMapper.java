package com.ernestocesario.myclothes.configurations.mappers;

import com.ernestocesario.myclothes.persistance.DTOs.ProductDTO;
import com.ernestocesario.myclothes.persistance.DTOs.ProductRequestDTO;
import com.ernestocesario.myclothes.persistance.entities.CartElement;
import com.ernestocesario.myclothes.persistance.entities.OrderProduct;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Named("toProductDTOFromProductVariant")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productVariantId", source = "id")
    @Mapping(target = "brand", source = "product.brand")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "category", source = "product.category")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "style", source = "style")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "price", source = "price")
    ProductDTO toProductDTO(ProductVariant productVariant);

    @Named("toProductDTOFromProductSnapshot")
    @Mapping(target = "productId", constant = "null")
    @Mapping(target = "productVariantId", constant = "null")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "style", source = "style")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "price", source = "price")
    ProductDTO toProductDTO(ProductSnapshot productSnapshot);

    @Named("toProductRequestDTOFromCartElement")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "productDTO", source = "productVariant", qualifiedByName = "toProductDTOFromProductVariant")
    ProductRequestDTO toProductRequestDTO(CartElement cartElement);

    @Named("toProductRequestDTOFromOrderProduct")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "productDTO", source = "productSnapshot", qualifiedByName = "toProductDTOFromProductSnapshot")
    ProductRequestDTO toProductRequestDTO(OrderProduct orderProduct);
}
