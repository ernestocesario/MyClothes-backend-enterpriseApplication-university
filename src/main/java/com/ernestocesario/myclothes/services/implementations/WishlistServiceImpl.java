package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import com.ernestocesario.myclothes.persistance.repositories.WishlistRepository;
import com.ernestocesario.myclothes.services.interfaces.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public Page<Wishlist> getAllPublicWishlists(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return wishlistRepository.findAllByPubIsTrue(pageable);
    }

    @Override
    public Page<Wishlist> getAllPublicWishlistsByKeyword(String keyword, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return wishlistRepository.findAllByPubIsTrueAndNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Wishlist> getMyWishlists(Pageable pageable) {
        User user = userServiceImpl.getCurrentUser();
        if (!user.isCustomer())
            throw new InternalServerErrorException();

        Customer customer = (Customer) user;

        return null;
    }

    @Override
    public Page<Wishlist> getWishlistSharedWithMe(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Wishlist> getWishlistsOfCustomer(String customerId, Pageable pageable) {
        return null;
    }

    @Override
    public Wishlist getWishlistById(String wishlistId) {
        return null;
    }

    @Override
    public boolean createWishlist(String wishlistName) {
        return false;
    }

    @Override
    public boolean deleteWishlist(String wishlistId) {
        return false;
    }

    @Override
    public boolean modifyWishlistVisibility(String wishlistId, boolean isPublic) {
        return false;
    }

    @Override
    public boolean shareWishlist(String wishlistId, String recipientCustomerEmail) {
        return false;
    }

    @Override
    public boolean unshareWishlist(String wishlistId, Customer recipientCustomer) {
        return false;
    }

    @Override
    public boolean addProductVariantToWishlist(String wishlistId, String productVariantId) {
        return false;
    }

    @Override
    public boolean removeProductVariantFromWishlist(String wishlistId, String productVariantId) {
        return false;
    }
}
