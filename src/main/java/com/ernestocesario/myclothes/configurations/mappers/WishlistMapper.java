package com.ernestocesario.myclothes.configurations.mappers;

import com.ernestocesario.myclothes.persistance.DTOs.FullWishlistDTO;
import com.ernestocesario.myclothes.persistance.DTOs.ProductDTO;
import com.ernestocesario.myclothes.persistance.DTOs.WishlistDTO;
import com.ernestocesario.myclothes.persistance.DTOs.WishlistSharingDTO;
import com.ernestocesario.myclothes.persistance.entities.ProductVariant;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import com.ernestocesario.myclothes.persistance.entities.WishlistAccess;
import com.ernestocesario.myclothes.persistance.entities.utils.WishlistPermission;
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
    WishlistDTO toWishlistDTO(Wishlist wishlist);

    default FullWishlistDTO toFullWishlistDTO(Wishlist wishlist) {
        FullWishlistDTO fullWishlistDTO = new FullWishlistDTO();
        WishlistDTO wishlistDTO = toWishlistDTO(wishlist);
        BeanUtils.copyProperties(wishlistDTO, fullWishlistDTO);

        fullWishlistDTO.setProducts(toProductDTOList(wishlist.getProductVariants()));

        return fullWishlistDTO;
    }

    default WishlistSharingDTO toWishlistSharingDTO(WishlistAccess wishlistAccess) {
        WishlistPermission wishlistPermission = wishlistAccess.getWishlistPermission();
        if (wishlistPermission != WishlistPermission.SHARED)
            return null;

        WishlistSharingDTO wishlistSharingDTO = new WishlistSharingDTO();
        wishlistSharingDTO.setId(wishlistAccess.getId());
        wishlistSharingDTO.setUserEmail(wishlistAccess.getCustomer().getEmail());

        return wishlistSharingDTO;
    }


    //Utility methods
    @Named("toProductDTOList")
    @IterableMapping(qualifiedByName = "toProductDTOFromProductVariant")
    List<ProductDTO> toProductDTOList(List<ProductVariant> productVariants);
}
