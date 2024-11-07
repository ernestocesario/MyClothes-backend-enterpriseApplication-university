package com.ernestocesario.myclothes.configurations.security.authorization.predicates.wishlist;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationTest;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.wishlist.WishlistNotExistsException;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import com.ernestocesario.myclothes.persistance.repositories.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishlistPublicOrCustomerOwnWishlistOrCustomerHasAccessWishlist extends AuthorizationTest {
    private final WishlistRepository wishlistRepository;
    private final CustomerOwnWishlistOrCustomerHasAccessWishlist customerOwnWishlistOrCustomerHasAccessWishlist;

    @Override
    protected boolean argumentCheck(Object... objects) {
        return objects.length == 1 && objects[0] instanceof String;
    }

    @Override
    protected boolean contextCheck(User user, Object... objects) {
        String wishlistId = (String) objects[0];

        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElseThrow(WishlistNotExistsException::new);

        if (wishlist.isPub())
            return true;

        return customerOwnWishlistOrCustomerHasAccessWishlist.test(user, wishlistId);
    }
}