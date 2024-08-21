package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.DTOs.*;
import com.ernestocesario.myclothes.persistance.entities.utils.CustomerShippingInfo;

import java.util.List;

public interface CustomerService {

    //smistare i metodi nei vari service

    public List<DiscountCodeDTO> getDiscountCodeOfCustomer();
    public CustomerProfileDTO getCustomerProfile();
    public List<OrderDTO> getOrderHistory();
    public FullOrderDTO getSpecificOrder(OrderDTO orderDTO);
    public List<ProductDTO> getProductsByKeyword(String keyword);  //this should search in brand, name and category (not in description)
    public ProductDTO getProductById(String id);
    public CartDTO getCart();
    public List<ReviewDTO> getReviewsByCustomer();
    public List<ReviewDTO> getReviewsByProductId(String productId);
    public List<WishlistDTO> getPrivateWishlistOfCustomer();
    public List<ProductRequestDTO> getWishlistProductsByWishlistId(String wishlistId);
    public List<WishlistSharingDTO> getUsersWithSharedWishlistByWishlistId();
    public List<WishlistDTO> getPublicWishlists();
    public List<WishlistDTO> getSharedWithMeWishlistOfCustomer();
    public FullWishlistDTO getItemsOfWishlist(WishlistDTO wishlistDTO);
    public List<ChatDTO> getChatsOfCustomer();
    public FullChatDTO getChatById(String chatId);

    public boolean setCustomerShippingInfo(CustomerShippingInfo customerShippingInfo);
    public boolean createOrderByCart();
    public boolean deleteOrder(OrderDTO orderDTO);
    public boolean addProductToCart(ProductDTO productDTO);
    public boolean deleteProductFromCart(ProductDTO productDTO);
    public boolean changeProductQuantityInCart(ProductRequestDTO productRequestDTO);
    public boolean addReviewToProductVariant(ProductDTO productDTO, ReviewDTO reviewDTO);
    public boolean deleteReviewFromProductVariant(ReviewDTO reviewDTO);
    public boolean createWishlist(WishlistDTO wishlistDTO);
    public boolean deleteWishlist(WishlistDTO wishlistDTO);  //admin can access this but only for deleting public wishlist. Customers can access this for deleting their own Wishlists.
    public boolean addProductToWishlist(WishlistDTO wishlistDTO, ProductDTO productDTO);
    public boolean deleteProductFromWishlist(WishlistDTO wishlistDTO, ProductDTO productDTO);
    public boolean copyWishlistContentToCart(WishlistDTO wishlistDTO);
    public boolean shareWishlistWithUser(WishlistDTO wishlistDTO, String userEmail);
    public boolean unshareWishlistWithUser(WishlistDTO wishlistDTO, String userEmail);  //wishlist owner can access this, and customer that has been shared with can access this
    public boolean setWishlistVisibility(WishlistDTO wishlistDTO, boolean isPublic);
    public boolean createChat();
    public boolean terminateChat(ChatDTO chatDTO);
    public FullChatDTO getFullChat(ChatDTO chatDTO);
    public boolean sendMessageToChat(ChatDTO chatDTO, String message);  //should not work if chat is terminated

    //Admins only
    public List<CustomerProfileDTO> getListOfAllCustomers();
    public List<CustomerProfileDTO> getListOfAllBannedCustomers();
    public List<UserProfileDTO> getListOfAllAdmins();
    public CustomerProfileDTO getCustomerProfileByEmail(String email);
    public List<OrderDTO> getOrdersByCustomerEmail(String email);
    public List<WishlistDTO> getAllPublicWishlist();
    public List<ChatDTO> getChatsByCustomerEmail(String email);
    public List<ChatDTO> getAllActiveChats();
    public List<ChatDTO> getAllTerminatedChat();

    public boolean setCustomerLimitations(CustomerProfileDTO customerProfileDTO, boolean isBanned);
    public boolean addBalanceToCustomer(UserProfileDTO userProfileDTO, double amount);  //only accessible to admins
    public boolean deleteOrderFromCustomer(CustomerProfileDTO customerProfileDTO, OrderDTO orderDTO);  //only accessible to admins
    public boolean createNewProduct(ProductDTO productDTO);  //only accessible to admins
    public boolean deleteProductVariant(ProductDTO productDTO);  //only accessible to admins
    public boolean deleteProductAndItsVariants(ProductDTO productDTO);  //only accessible to admins
}
