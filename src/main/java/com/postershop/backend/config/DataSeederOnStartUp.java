package com.postershop.backend.config;

import com.postershop.backend.entity.User;
import com.postershop.backend.entity.enums.Role;
import com.postershop.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataSeederOnStartUp implements CommandLineRunner {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ADMIN_USERNAME;
    private final String ADMIN_PASSWORD;

    public DataSeederOnStartUp(UserRepository userRepository,
                               PasswordEncoder passwordEncoder,
                               @Value("${app.admin.username}") String adminUsername,
                               @Value("${app.admin.password}") String adminPassword) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        ADMIN_USERNAME = adminUsername;
        ADMIN_PASSWORD = adminPassword;
    }

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername(ADMIN_USERNAME);
            adminUser.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            adminUser.setRole(Role.ADMIN);
            userRepository.save(adminUser);
            log.info("Admin user created with username: {}", ADMIN_USERNAME);

        }else{
            log.info("Admin user already exists. Skipping creation....");
        }

    }
}
