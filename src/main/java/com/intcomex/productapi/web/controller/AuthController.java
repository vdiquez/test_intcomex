package com.intcomex.productapi.web.controller;

import com.intcomex.productapi.domain.repository.UserRepository;
import com.intcomex.productapi.infrastructure.config.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Operation(summary = "post authentication")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.getUsername(), request.getPassword()
        ));

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Getter
    @Setter
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
    }
}
