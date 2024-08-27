package com.ernestocesario.myclothes.configurations.security.authorization.predicates;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationTest;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import com.ernestocesario.myclothes.persistance.repositories.WishlistShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerHasAccessWishlist extends AuthorizationTest {
    private final IsAdmin isAdmin;
    private final WishlistShareRepository wishlistShareRepository;

    @Override
    protected boolean argumentCheck(Object... objects) {
        return objects.length == 1 && objects[0] instanceof Wishlist;
    }

    @Override
    protected boolean contextCheck(User user, Object... objects) {
        Wishlist wishlist = (Wishlist) objects[0];

        if (isAdmin.test(user))
            return false;

        Customer customer = (Customer) user;
        return wishlistShareRepository.existsWishlistShareByWishlistAndCustomer(wishlist, customer);
    }
}
