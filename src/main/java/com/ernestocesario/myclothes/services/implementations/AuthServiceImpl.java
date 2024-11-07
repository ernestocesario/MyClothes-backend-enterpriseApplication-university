package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.UserMapper;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.auth.InvalidGoogleIdTokenException;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.auth.AuthResponseDTO;
import com.ernestocesario.myclothes.persistance.entities.Admin;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.repositories.AdminRepository;
import com.ernestocesario.myclothes.persistance.repositories.AllowedAdminRepository;
import com.ernestocesario.myclothes.persistance.repositories.CustomerRepository;
import com.ernestocesario.myclothes.persistance.repositories.UserRepository;
import com.ernestocesario.myclothes.services.interfaces.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final AllowedAdminRepository allowedAdminRepository;
    private final GoogleIdTokenVerifierServiceImpl googleIdTokenVerifierService;

    private final UserMapper userMapper;
    private final UserServiceImpl userServiceImpl;


    @Override
    @Transactional
    public AuthResponseDTO login(String idTokenString) {
        try {
            GoogleIdToken.Payload payload = googleIdTokenVerifierService.verifyIdToken(idTokenString);

            String email = payload.getEmail();
            User user = userRepository.findByEmail(email);

            if (user == null) {  //user not registered
                if (allowedAdminRepository.existsByAdminEmail(email))
                    user = registerAdmin(payload);
                else
                    user = registerCustomer(payload);
            }

            jwtService.generateAccessToken(user);
            jwtService.generateRefreshToken(user);

            return userMapper.toAuthResponseDTO(user);
        }
        catch (GeneralSecurityException | IOException | RuntimeException e) {
            throw new InvalidGoogleIdTokenException();
        }
        catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }

    @Override
    @Transactional
    public Boolean logout() {
        User user = userServiceImpl.getCurrentUser();
        user.setAccessToken(null);
        user.setRefreshToken(null);
        userRepository.save(user);

        return true;
    }



    //private methods
    private User registerCustomer(GoogleIdToken.Payload payload) {
        Customer customer = new Customer();

        registerUser(customer, payload);
        customerRepository.save(customer);

        return customer;
    }

    private Admin registerAdmin(GoogleIdToken.Payload payload) {
        Admin admin = new Admin();

        registerUser(admin, payload);
        adminRepository.save(admin);

        return admin;
    }

    private void registerUser(User user, GoogleIdToken.Payload payload) {
        user.setUsername(payload.get("given_name") + " " + payload.get("family_name"));
        user.setEmail(payload.getEmail());
        user.setImageUrl((String) payload.get("picture"));
    }
}
