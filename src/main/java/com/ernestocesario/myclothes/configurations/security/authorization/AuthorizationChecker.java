package com.ernestocesario.myclothes.configurations.security.authorization;

import com.ernestocesario.myclothes.exceptions.InvalidAuthorizationException;
import com.ernestocesario.myclothes.persistance.entities.User;

public class AuthorizationChecker {
    private AuthorizationChecker() {}  //no instantiation

    public static void check(AuthorizationTest authorizationTest, User user, Object... objects) {
        if (!authorizationTest.test(user, objects))
            throw new InvalidAuthorizationException();
    }
}
