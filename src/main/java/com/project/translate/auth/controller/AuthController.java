package com.project.translate.auth.controller;

import com.project.translate.auth.dto.request.LoginRequest;
import com.project.translate.auth.dto.request.RegistrationRequest;
import com.project.translate.auth.dto.response.LoginResponse;
import com.project.translate.auth.dto.response.RegistrationResponse;
import com.project.translate.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest request) {
        RegistrationResponse registrationResponse = authService.registerUser(request);
        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.loginUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
