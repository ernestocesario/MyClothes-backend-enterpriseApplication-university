package com.ernestocesario.myclothes.configurations.security.authorization.predicates.customer;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationTest;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsAdmin;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOwnCustomerOrIsAdmin extends AuthorizationTest {
    private final IsAdmin isAdmin;

    @Override
    protected boolean argumentCheck(Object... objects) {
        return objects.length == 1 && objects[0] instanceof String;
    }

    @Override
    protected boolean contextCheck(User user, Object... objects) {
        String customerId = (String) objects[0];

        return isAdmin.test(user) || (user instanceof Customer && user.getId().equals(customerId));
    }
}
