package com.postershop.backend.controller.auth;

import com.postershop.backend.dto.auth.LoginRequest;
import com.postershop.backend.service.authentication.AuthService;
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
    public String login(@RequestBody LoginRequest loginRequest) {

        return authService.loginService(loginRequest.getUsername(), loginRequest.getPassword());

    }

    // Endpoint for user registration (CUSTOMER)
    @PostMapping("/register")
    public String register(@RequestBody LoginRequest registerRequest) {

        return authService.registerService(registerRequest.getUsername(), registerRequest.getPassword());

    }

}
