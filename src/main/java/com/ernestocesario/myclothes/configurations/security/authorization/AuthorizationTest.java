package com.ernestocesario.myclothes.configurations.security.authorization;

import com.ernestocesario.myclothes.persistance.entities.User;

public abstract class AuthorizationTest {
    public final boolean test(User user, Object... objects) {
        if (!argumentCheck(objects))
            return false;

        return contextCheck(user, objects);
    }

    protected abstract boolean argumentCheck(Object... objects);
    protected abstract boolean contextCheck(User user, Object... objects);
}
