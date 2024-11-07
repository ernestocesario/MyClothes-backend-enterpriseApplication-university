package com.ernestocesario.myclothes.controllers;

import com.ernestocesario.myclothes.exceptions.auth.InvalidGoogleIdTokenException;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.auth.IdTokenDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.auth.AuthResponseDTO;
import com.ernestocesario.myclothes.persistance.DTOs.businessLogic.review.FullReviewDTO;
import com.ernestocesario.myclothes.services.implementations.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${authControllerPath}", produces = "application/json")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping("${loginAuthControllerSubPath}")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody IdTokenDTO idTokenDTO) throws InvalidGoogleIdTokenException {
        AuthResponseDTO authResponseDTO = authServiceImpl.login(idTokenDTO.getIdToken());
        return ResponseEntity.ok(authResponseDTO);
    }

    @GetMapping("${logoutAuthControllerSubPath}")
    public ResponseEntity<Boolean> logout() {
        return ResponseEntity.ok(authServiceImpl.logout());
    }
}