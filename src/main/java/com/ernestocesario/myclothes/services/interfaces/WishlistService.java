package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;

import java.util.List;

public interface WishlistService {
    List<Wishlist> getAllPublicWishlists();
    List<Wishlist> getWishlistsOfCustomer(Customer customer);
    List<Wishlist> getWishlistSharedWith(Customer customer);
    Wishlist getWishlistById(String wishlistId);

    boolean createWishlist(Customer customer, String wishlistName);
    boolean deleteWishlist(Customer customer, String wishlistId);
    boolean modifyWishlistVisibility(Customer customer, String wishlistId, boolean isPublic);
    boolean shareWishlist(Customer customer, String wishlistId, Customer recipientCustomer);
    boolean unshareWishlist(Customer customer, String wishlistId, Customer recipientCustomer);
    boolean addProductVariantToWishlist(Customer customer, String wishlistId, String productVariantId);
    boolean removeProductVariantFromWishlist(Customer customer, String wishlistId, String productVariantId);
}