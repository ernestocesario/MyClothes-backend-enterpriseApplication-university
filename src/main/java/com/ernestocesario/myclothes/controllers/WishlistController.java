package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.WishlistMapper;
import com.ernestocesario.myclothes.exceptions.InvalidInputException;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist.FullWishlistDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.wishlist.WishlistDTO;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import com.ernestocesario.myclothes.services.implementations.WishlistServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${wishlistsControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistServiceImpl wishlistServiceImpl;
    private final WishlistMapper wishlistMapper;

    @GetMapping("${publicWishlistsControllerSubPath}")
    public ResponseEntity<Page<WishlistDTO>> getAllPublicWishlists(Pageable pageable) {
        Page<Wishlist> wishlistPage = wishlistServiceImpl.getAllPublicWishlists(pageable);
        return ResponseEntity.ok(wishlistPage.map(wishlistMapper::toWishlistDTO));
    }

    @GetMapping(value = "${publicWishlistsControllerSubPath}", params = "keyword")
    public ResponseEntity<Page<WishlistDTO>> getAllPublicWishlistByKeyword(@RequestParam String keyword, Pageable pageable) {
        Page<Wishlist> wishlistPage = wishlistServiceImpl.getAllPublicWishlistsByKeyword(keyword, pageable);
        return ResponseEntity.ok(wishlistPage.map(wishlistMapper::toWishlistDTO));
    }

    @GetMapping("")
    public ResponseEntity<Page<WishlistDTO>> getMyWishlists(Pageable pageable) {
        Page<Wishlist> wishlistPage = wishlistServiceImpl.getMyWishlists(pageable);
        return ResponseEntity.ok(wishlistPage.map(wishlistMapper::toWishlistDTO));
    }

    @GetMapping("${sharedWithMeWishlistsControllerSubPath}")
    public ResponseEntity<Page<WishlistDTO>> getWishlistsSharedWithMe(Pageable pageable) {
        Page<Wishlist> wishlistPage = wishlistServiceImpl.getWishlistsSharedWithMe(pageable);
        return ResponseEntity.ok(wishlistPage.map(wishlistMapper::toWishlistDTO));
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<FullWishlistDTO> getWishlist(@PathVariable String wishlistId) {
        Wishlist wishlist = wishlistServiceImpl.getWishlistById(wishlistId);
        return ResponseEntity.ok(wishlistMapper.toFullWishlistDTO(wishlist));
    }

    @PostMapping("")
    public ResponseEntity<Void> createWishlist(@Valid @RequestBody WishlistDTO wishlistDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new InvalidInputException();

        wishlistServiceImpl.createWishlist(wishlistDTO.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable String wishlistId) {
        wishlistServiceImpl.deleteWishlist(wishlistId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{wishlistId}${visibilityWishlistsControllerSubPath}/{visibility}")
    public ResponseEntity<Void> changeWishlistVisibility(@PathVariable String wishlistId, @PathVariable boolean visibility) {
        wishlistServiceImpl.modifyWishlistVisibility(wishlistId, visibility);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{wishlistId}${shareWishlistsControllerSubPath}/{customerRecipientEmail}")
    public ResponseEntity<Void> shareWishlist(@PathVariable String wishlistId, @PathVariable String customerRecipientEmail) {
        wishlistServiceImpl.shareWishlist(wishlistId, customerRecipientEmail);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{wishlistId}${unShareWishlistsControllerSubPath}/{customerRecipientEmail}")
    public ResponseEntity<Void> unShareWishlist(@PathVariable String wishlistId, @PathVariable String customerRecipientEmail) {
        wishlistServiceImpl.unshareWishlist(wishlistId, customerRecipientEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{wishlistId}${productWishlistsControllerSubPath}/{productVariantId}")
    public ResponseEntity<Void> addProductVariantToWishlist(@PathVariable String wishlistId, @PathVariable String productVariantId) {
        wishlistServiceImpl.addProductVariantToWishlist(wishlistId, productVariantId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{wishlistId}${productWishlistsControllerSubPath}/{productVariantId}")
    public ResponseEntity<Void> removeProductVariantFromWishlist(@PathVariable String wishlistId, @PathVariable String productVariantId) {
        wishlistServiceImpl.removeProductVariantFromWishlist(wishlistId, productVariantId);
        return ResponseEntity.ok().build();
    }
}