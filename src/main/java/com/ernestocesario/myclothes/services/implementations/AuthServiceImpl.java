package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.mappers.businessLogic.UserMapper;
import com.ernestocesario.myclothes.exceptions.InternalServerErrorException;
import com.ernestocesario.myclothes.exceptions.auth.InvalidGoogleIdTokenException;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.users.AuthResponseDTO;
import com.ernestocesario.myclothes.persistance.entities.Admin;
import com.ernestocesario.myclothes.persistance.entities.Customer;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.repositories.AdminRepository;
import com.ernestocesario.myclothes.persistance.repositories.AllowedAdminRepository;
import com.ernestocesario.myclothes.persistance.repositories.CustomerRepository;
import com.ernestocesario.myclothes.persistance.repositories.UserRepository;
import com.ernestocesario.myclothes.services.interfaces.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.oauth2.TokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final AllowedAdminRepository allowedAdminRepository;

    private final UserMapper userMapper;


    @Override
    @Transactional
    public AuthResponseDTO login(String idTokenString) {
        GoogleIdTokenVerifier googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(""))
                .build();

        try {
            GoogleIdToken idToken = googleIdTokenVerifier.verify(idTokenString);

            if (idToken == null)
                throw new TokenVerifier.VerificationException("Invalid token");

            GoogleIdToken.Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            User user = userRepository.findByEmail(email);

            if (user != null) {
                try {
                    jwtService.generateAccessToken(user);
                    jwtService.generateRefreshToken(user);

                    return userMapper.toAuthResponseDTO(user);
                }
                catch (Exception e) {
                    throw new InternalServerErrorException();
                }
            }
            else {  //user not registered
                User newUser;
                if (allowedAdminRepository.existsByAdminEmail(email)) {
                    allowedAdminRepository.deleteByAdminEmail(email);

                    newUser = registerAdmin(payload);
                }
                else
                    newUser = registerCustomer(payload);

                return userMapper.toAuthResponseDTO(newUser);
            }
        }
        catch (GeneralSecurityException | IOException | TokenVerifier.VerificationException | RuntimeException e) {
            throw new InvalidGoogleIdTokenException();
        }
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
