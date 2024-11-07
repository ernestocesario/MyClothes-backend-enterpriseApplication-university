package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationChecker;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsCustomer;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.wishlist.CustomerOwnWishlist;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.wishlist.CustomerOwnWishlistOrCustomerHasAccessWishlist;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.wishlist.WishlistPublicOrCustomerOwnWishlistOrCustomerHasAccessWishlist;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.wishlist.CustomerOwnWishlistOrIsAdminAndWishlistPublic;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.InvalidInputException;
import com.ernestocesario.myclothes.exceptions.wishlist.WishlistAlreadySharedWithCustomerException;
import com.ernestocesario.myclothes.persistance.entities.*;
import com.ernestocesario.myclothes.persistance.repositories.*;
import com.ernestocesario.myclothes.services.interfaces.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserServiceImpl userServiceImpl;
    private final IsCustomer isCustomer;
    private final WishlistPublicOrCustomerOwnWishlistOrCustomerHasAccessWishlist wishlistPublicOrCustomerOwnWishlistOrCustomerHasAccessWishlist;
    private final CustomerOwnWishlistOrIsAdminAndWishlistPublic customerOwnWishlistOrIsAdminAndWishlistPublic;
    private final CustomerOwnWishlist customerOwnWishlist;
    private final CustomerRepository customerRepository;
    private final CustomerOwnWishlistOrCustomerHasAccessWishlist customerOwnWishlistOrCustomerHasAccessWishlist;
    private final WishlistShareRepository wishlistShareRepository;
    private final ProductVariantRepository productVariantRepository;
    private final WishlistProductRepository wishlistProductRepository;

    @Override
    @Transactional
    public Page<Wishlist> getAllPublicWishlists(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return wishlistRepository.findAllByPubIsTrue(pageable);
    }

    @Override
    @Transactional
    public Page<Wishlist> getAllPublicWishlistsByKeyword(String keyword, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return wishlistRepository.findAllByPubIsTrueAndNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    @Transactional
    public Page<Wishlist> getMyWishlists(Pageable pageable) {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return wishlistRepository.findAllByCustomer(customer, pageable);
    }

    @Override
    @Transactional
    public Page<Wishlist> getWishlistsSharedWithMe(Pageable pageable) {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return wishlistRepository.findByWishlistSharesCustomer(customer, pageable);
    }

    @Override
    @Transactional
    public Wishlist getWishlistById(String wishlistId) {
        AuthorizationChecker.check(wishlistPublicOrCustomerOwnWishlistOrCustomerHasAccessWishlist, userServiceImpl.getCurrentUser(), wishlistId);

        return wishlistRepository.findById(wishlistId).orElseThrow(InternalServerErrorException::new);
    }

    @Override
    @Transactional
    public boolean createWishlist(String wishlistName) {
        AuthorizationChecker.check(isCustomer, userServiceImpl.getCurrentUser());

        Customer customer = (Customer) userServiceImpl.getCurrentUser();

        Wishlist wishlist = new Wishlist();
        wishlist.setName(wishlistName);
        wishlist.setCustomer(customer);

        wishlistRepository.save(wishlist);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteWishlist(String wishlistId) {
        AuthorizationChecker.check(customerOwnWishlistOrIsAdminAndWishlistPublic, userServiceImpl.getCurrentUser(), wishlistId);

        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);
        if (wishlist == null)
            throw new InternalServerErrorException();

        wishlistRepository.delete(wishlist);

        return true;
    }

    @Override
    @Transactional
    public boolean modifyWishlistVisibility(String wishlistId, boolean isPublic) {
        AuthorizationChecker.check(customerOwnWishlist, userServiceImpl.getCurrentUser(), wishlistId);

        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(InternalServerErrorException::new);

        wishlist.setPub(isPublic);
        wishlistRepository.save(wishlist);

        return true;
    }

    @Override
    @Transactional
    public boolean shareWishlist(String wishlistId, String recipientCustomerEmail) {
        AuthorizationChecker.check(customerOwnWishlist, userServiceImpl.getCurrentUser(), wishlistId);

        Customer recipientCustomer = customerRepository.findByEmail(recipientCustomerEmail).orElseThrow(InvalidInputException::new);
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(InternalServerErrorException::new);

        if (wishlistShareRepository.findByWishlistAndCustomer_Email(wishlist, recipientCustomerEmail).isPresent())
            throw new WishlistAlreadySharedWithCustomerException();

        WishlistShare wishlistShare = new WishlistShare();
        wishlistShare.setWishlist(wishlist);
        wishlistShare.setCustomer(recipientCustomer);

        wishlistShareRepository.save(wishlistShare);

        return true;
    }

    @Override
    @Transactional
    public boolean unshareWishlist(String wishlistId, String recipientCustomerEmail) {
        AuthorizationChecker.check(customerOwnWishlistOrCustomerHasAccessWishlist, userServiceImpl.getCurrentUser(), wishlistId);

        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(InternalServerErrorException::new);

        WishlistShare wishlistShare = wishlistShareRepository.findByWishlistAndCustomer_Email(wishlist, recipientCustomerEmail).orElseThrow(InternalServerErrorException::new);
        wishlistShareRepository.delete(wishlistShare);

        return true;
    }

    @Override
    @Transactional
    public boolean addProductVariantToWishlist(String wishlistId, String productVariantId) {
        AuthorizationChecker.check(customerOwnWishlist, userServiceImpl.getCurrentUser(), wishlistId);

        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(InternalServerErrorException::new);
        ProductVariant productVariant = productVariantRepository.findById(productVariantId).orElseThrow(InternalServerErrorException::new);

        WishlistProduct wishlistProduct = new WishlistProduct();
        wishlistProduct.setWishlist(wishlist);
        wishlistProduct.setProductVariant(productVariant);

        wishlistProductRepository.save(wishlistProduct);

        return true;
    }

    @Override
    @Transactional
    public boolean removeProductVariantFromWishlist(String wishlistId, String productVariantId) {
        AuthorizationChecker.check(customerOwnWishlist, userServiceImpl.getCurrentUser(), wishlistId);

        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(InternalServerErrorException::new);
        ProductVariant productVariant = productVariantRepository.findById(productVariantId).orElseThrow(InternalServerErrorException::new);

        WishlistProduct wishlistProduct = wishlistProductRepository.findByWishlistAndProductVariant(wishlist, productVariant).orElseThrow(InternalServerErrorException::new);

        wishlistProductRepository.delete(wishlistProduct);

        return true;
    }
}
