package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.AppUserDetails;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.entities.utils.UserRole;
import com.ernestocesario.myclothes.persistance.repositories.UserRepository;
import com.ernestocesario.myclothes.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Value("${testing}")
    private boolean testing;

    @Override
    @Transactional
    public User getUserByEmail(String email) {  //only system
        User user = userRepository.findByEmail(email);

        if(user == null)
            throw new EntityNotFoundException();
        return user;
    }

    @Override
    public User getCurrentUser() {
        if (testing)
            return null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        return appUserDetails.user();
    }
}
