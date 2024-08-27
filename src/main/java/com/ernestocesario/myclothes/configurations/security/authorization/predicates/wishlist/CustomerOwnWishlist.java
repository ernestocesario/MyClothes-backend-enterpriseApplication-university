package com.ernestocesario.myclothes.configurations.security.authorization.predicates.wishlist;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationTest;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsAdmin;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.repositories.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOwnWishlist extends AuthorizationTest {
    private final IsAdmin isAdmin;
    private final WishlistRepository wishlistRepository;

    @Override
    protected boolean argumentCheck(Object... objects) {
        return objects.length == 1 && objects[0] instanceof String;
    }

    @Override
    protected boolean contextCheck(User user, Object... objects) {
        String wishlistId = (String) objects[0];

        if(isAdmin.test(user))
            return false;

        Customer customer = (Customer) user;
        return wishlistRepository.existsByIdAndCustomer(wishlistId, customer);
    }
}
