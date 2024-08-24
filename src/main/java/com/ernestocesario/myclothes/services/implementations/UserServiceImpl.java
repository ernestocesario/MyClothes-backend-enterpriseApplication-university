package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.AppUserDetails;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.entities.utils.UserRole;
import com.ernestocesario.myclothes.persistance.repositories.UserRepository;
import com.ernestocesario.myclothes.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null)
            throw new EntityNotFoundException("User not found");
        return user;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        return appUserDetails.user();
    }

    @Override
    public boolean isCurrentUserAdmin() {
        return getCurrentUser().getRole().equals(UserRole.ADMIN);
    }
}
