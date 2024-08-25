package com.ernestocesario.myclothes.configurations.mappers.businessLogic;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.*;
import com.ernestocesario.myclothes.persistance.entities.CartElement;
import com.ernestocesario.myclothes.persistance.entities.OrderProduct;
import com.ernestocesario.myclothes.persistance.entities.Product;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductSnapshot;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Named("toProductDTOFromProduct")
    @Mapping(target = "productId", source = "id")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category", source = "category")
    ProductDTO toProductDTO(Product product);

    @Named("toFullProductDTOFromProduct")
    default FullProductDTO toFullProductDTO(Product product) {
        ProductDTO productDTO = toProductDTO(product);
        FullProductDTO fullProductDTO = new FullProductDTO();
        BeanUtils.copyProperties(productDTO, fullProductDTO);

        fullProductDTO.setProductVariants(toProductVariantDTOList(product.getProductVariants()));

        return fullProductDTO;
    }

    @Named("toProductVariantDTOFromProductVariant")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productVariantId", source = "id")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "style", source = "style")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "price", source = "price")
    ProductVariantDTO toProductVariantDTO(ProductVariant productVariant);

    @Named("toFullProductVariantDTOFromProductVariant")
    default FullProductVariantDTO toFullProductVariantDTO(ProductVariant productVariant) {
        ProductVariantDTO productVariantDTO = toProductVariantDTO(productVariant);
        FullProductVariantDTO fullProductVariantDTO = new FullProductVariantDTO();
        BeanUtils.copyProperties(productVariantDTO, fullProductVariantDTO);

        fullProductVariantDTO.setBrand(productVariant.getProduct().getBrand());
        fullProductVariantDTO.setName(productVariant.getProduct().getName());
        fullProductVariantDTO.setDescription(productVariant.getProduct().getDescription());
        fullProductVariantDTO.setCategory(productVariant.getProduct().getCategory());

        return fullProductVariantDTO;
    }

    @Named("toFullProductVariantDTOFromProductSnapshot")
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
    FullProductVariantDTO toFullProductVariantDTO(ProductSnapshot productSnapshot);

    @Named("toProductRequestDTOFromCartElement")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "fullProductVariantDTO", source = "productVariant", qualifiedByName = "toFullProductVariantDTOFromProductVariant")
    ProductRequestDTO toProductRequestDTO(CartElement cartElement);

    @Named("toProductRequestDTOFromOrderProduct")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "fullProductVariantDTO", source = "productSnapshot", qualifiedByName = "toFullProductVariantDTOFromProductSnapshot")
    ProductRequestDTO toProductRequestDTO(OrderProduct orderProduct);



    // Utility methods
    @IterableMapping(qualifiedByName = "toProductVariantDTOFromProductVariant")
    List<ProductVariantDTO> toProductVariantDTOList(List<ProductVariant> productVariants);
}
