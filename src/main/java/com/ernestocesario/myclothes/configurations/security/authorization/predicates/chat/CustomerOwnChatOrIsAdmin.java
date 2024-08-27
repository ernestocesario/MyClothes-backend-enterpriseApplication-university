package com.ernestocesario.myclothes.configurations.security.authorization.predicates.chat;

import com.ernestocesario.myclothes.configurations.security.authorization.AuthorizationTest;
import com.ernestocesario.myclothes.configurations.security.authorization.predicates.user.IsAdmin;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.repositories.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerOwnChatOrIsAdmin extends AuthorizationTest {
    private final ChatRepository chatRepository;
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
        String chatId = (String) objects[0];

        return chatRepository.existsChatByIdAndCustomer(chatId, customer);
    }
}
