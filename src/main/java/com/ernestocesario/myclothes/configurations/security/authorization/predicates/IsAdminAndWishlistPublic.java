package com.ernestocesario.myclothes.configurations.security.authorization.predicates;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationTest;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.entities.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IsAdminAndWishlistPublic extends AuthorizationTest {
    private final IsAdmin isAdmin;

    @Override
    protected boolean argumentCheck(Object... objects) {
        return objects.length == 1 && objects[0] instanceof Wishlist;
    }

    @Override
    protected boolean contextCheck(User user, Object... objects) {
        Wishlist wishlist = (Wishlist) objects[0];

        return isAdmin.test(user) && wishlist.isPub();
    }
}
