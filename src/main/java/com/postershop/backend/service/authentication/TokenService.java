package com.postershop.backend.service.authentication;


import org.springframework.security.core.Authentication;

    // Token service interface for generating authentication tokens

public interface TokenService {

    String generateToken(Authentication authentication);

}
