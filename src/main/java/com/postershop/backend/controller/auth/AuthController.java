package com.postershop.backend.controller.auth;

import com.postershop.backend.dto.auth.LoginRequest;
import com.postershop.backend.service.authentication.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    // REST controller for handling authentication requests

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        String token = authService.loginService(loginRequest.getUsername(), loginRequest.getPassword());

        return ResponseEntity.ok(token);

    }

    // Endpoint for user registration (CUSTOMER)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest registerRequest) {

        String message = authService.registerService(registerRequest.getUsername(), registerRequest.getPassword());

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

}
