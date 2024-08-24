package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserService {
    User getUserByEmail(String email);

    User getCurrentUser();
    boolean isCurrentUserAdmin();
}
