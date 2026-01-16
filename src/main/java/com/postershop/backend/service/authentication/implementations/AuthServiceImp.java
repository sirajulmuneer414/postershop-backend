package com.postershop.backend.service.authentication.implementations;

import com.postershop.backend.entity.User;
import com.postershop.backend.entity.enums.Role;
import com.postershop.backend.repository.UserRepository;
import com.postershop.backend.service.authentication.AuthService;
import com.postershop.backend.service.authentication.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

    // Implementation of the AuthService interface

@Service
public class AuthServiceImp implements AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImp(TokenService tokenService,
                          AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticates a user and generates a token upon successful login.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A JWT token if authentication is successful.
     */
    @Override
    public String loginService(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );


        return tokenService.generateToken(authentication);

    }

    /**
     * Registers a new user with the provided username and password.
     *
     * @param username The desired username for the new user.
     * @param password The desired password for the new user.
     * @return A message indicating the result of the registration process.
     */
    @Override
    public String registerService(String username, String password) {

        if(userRepository.findByUsername(username).isPresent()) {
            return "Username already exists";
        }

        User newUser = new User();

        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));

        newUser.setRole(Role.CUSTOMER);
        userRepository.save(newUser);

        return "User registered successfully";

    }
}
