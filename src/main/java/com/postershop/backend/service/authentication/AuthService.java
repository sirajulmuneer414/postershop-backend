package com.postershop.backend.service.authentication;

    // Authentication service interface for handling authentication-related operations

import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    String loginService(String username, String password);

    String registerService(String username, String password);
}
