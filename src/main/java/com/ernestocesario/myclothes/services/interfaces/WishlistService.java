package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WishlistService {
    Page<Wishlist> getAllPublicWishlists(Pageable pageable);
    Page<Wishlist> getAllPublicWishlistsByKeyword(String keyword, Pageable pageable);
    Page<Wishlist> getMyWishlists(Pageable pageable);  //only customer
    Page<Wishlist> getWishlistSharedWithMe(Pageable pageable);  //only customers
    Wishlist getWishlistById(String wishlistId);

    boolean createWishlist(String wishlistName);  //only customer
    boolean deleteWishlist(String wishlistId);
    boolean modifyWishlistVisibility(String wishlistId, boolean isPublic);  //only customer
    boolean shareWishlist(String wishlistId, String recipientCustomerEmail);  //only customer
    boolean unshareWishlist(String wishlistId, String recipientCustomerEmail);  //only customer
    boolean addProductVariantToWishlist(String wishlistId, String productVariantId);  //only customer
    boolean removeProductVariantFromWishlist(String wishlistId, String productVariantId);  //only customer
}