package com.ernestocesario.myclothes.configurations.security.authorization.predicates;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationTest;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOwnReviewOrIsAdmin extends AuthorizationTest {
    private final ReviewRepository reviewRepository;
    private final IsAdmin isAdmin;

    @Override
    protected boolean argumentCheck(Object... objects) {
        return objects.length == 1 && objects[0] instanceof String;
    }

    @Override
    protected boolean contextCheck(User user, Object... objects) {
        if (isAdmin.test(user))
            return true;

        Customer customer = (Customer) user;
        String reviewId = (String) objects[0];

        return reviewRepository.existsReviewsByIdAndCustomer(reviewId, customer);
    }
}
