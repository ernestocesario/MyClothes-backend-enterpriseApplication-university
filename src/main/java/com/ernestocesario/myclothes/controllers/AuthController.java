package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.exceptions.InvalidGoogleIdTokenException;
import com.ernestocesario.myclothes.persistance.DTOs.AuthResponseDTO;
import com.ernestocesario.myclothes.services.implementations.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody String idTokenString) throws InvalidGoogleIdTokenException {
        AuthResponseDTO authResponseDTO = authServiceImpl.login(idTokenString);
        return ResponseEntity.ok(authResponseDTO);
    }
}
