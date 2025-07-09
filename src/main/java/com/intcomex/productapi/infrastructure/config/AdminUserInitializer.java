package com.intcomex.productapi.infrastructure.config;

import com.intcomex.productapi.domain.model.User;
import com.intcomex.productapi.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Profile("!test") // Para evitar que se ejecute en pruebas
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.admin-user}")
    private String rawAdminUser;

    @Value("${security.admin-password}")
    private String rawAdminPassword;

    @Value("${security.admin-role}")
    private String rawAdminRole;

    @Override
    public void run(String... args) {
        String username = rawAdminUser;
        if (userRepository.findByUsername(username).isEmpty()) {
            User admin = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(rawAdminPassword)) // generado dinámicamente
                    .roles(Set.of(rawAdminRole))
                    .build();

            userRepository.save(admin);
            System.out.println("Usuario admin creado con contraseña: " + rawAdminPassword);
        }
    }
}