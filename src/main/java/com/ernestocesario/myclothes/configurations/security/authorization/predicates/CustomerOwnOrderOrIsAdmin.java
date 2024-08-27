package com.ernestocesario.myclothes.configurations.security.authorization.predicates;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationTest;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOwnOrderOrIsAdmin extends AuthorizationTest {
    private final IsAdmin isAdmin;
    private final OrderRepository orderRepository;

    @Override
    protected boolean argumentCheck(Object... objects) {
        return objects.length == 1 && objects[0] instanceof String;
    }

    @Override
    protected boolean contextCheck(User user, Object... objects) {
        String orderId = (String) objects[0];

        if (isAdmin.test(user))
            return true;
        Customer customer = (Customer) user;

        return orderRepository.existsOrderByIdAndCustomer(orderId, customer);
    }
}
