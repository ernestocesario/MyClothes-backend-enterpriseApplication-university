package com.ernestocesario.myclothes.configurations.mappers.businessLogic;

import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist.FullWishlistDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.product.FullProductVariantDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist.WishlistDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist.WishlistShareDTO;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import com.ernestocesario.myclothes.persistance.entities.WishlistProduct;
import com.ernestocesario.myclothes.persistance.entities.WishlistShare;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface WishlistMapper {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "pub", source = "pub")
    WishlistDTO toWishlistDTO(Wishlist wishlist);

    default FullWishlistDTO toFullWishlistDTO(Wishlist wishlist) {
        FullWishlistDTO fullWishlistDTO = new FullWishlistDTO();
        WishlistDTO wishlistDTO = toWishlistDTO(wishlist);
        BeanUtils.copyProperties(wishlistDTO, fullWishlistDTO);

        fullWishlistDTO.setProducts(toFullProductVariantDTOListFromWishlistProductList(wishlist.getWishlistProducts()));

        return fullWishlistDTO;
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "wishlistId", source = "wishlist.id")
    @Mapping(target = "userEmail", source = "customer.email")
    WishlistShareDTO toWishlistSharesDTO(WishlistShare wishlistShare);



    //Utility methods
    @Named("toFullProductVariantDTOListFromProductVariantList")
    @IterableMapping(qualifiedByName = "toFullProductVariantDTOFromProductVariant")
    List<FullProductVariantDTO> toFullProductVariantDTOListFromProductVariantList(List<ProductVariant> productVariants);

    @IterableMapping(qualifiedByName = "toFullProductVariantDTOFromWishlistProduct")
    List<FullProductVariantDTO> toFullProductVariantDTOListFromWishlistProductList(List<WishlistProduct> wishlistProducts);
}
