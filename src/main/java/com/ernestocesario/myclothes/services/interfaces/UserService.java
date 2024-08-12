package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.User;

public interface UserService {
    public User getUserByEmail(String email);
}
