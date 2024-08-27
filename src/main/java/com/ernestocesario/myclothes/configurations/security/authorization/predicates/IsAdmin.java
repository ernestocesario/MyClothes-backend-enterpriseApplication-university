package com.ernestocesario.myclothes.configurations.security.authorization.predicates;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationTest;
import com.ernestocesario.myclothes.persistance.entities.User;
import org.springframework.stereotype.Component;

@Component
public class IsAdmin extends AuthorizationTest {
    @Override
    protected boolean argumentCheck(Object... objects) {
        return objects.length == 0;
    }

    @Override
    protected boolean contextCheck(User user, Object... objects) {
        return user.isAdmin();
    }
}
