package com.intcomex.productapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intcomex.productapi.domain.model.User;
import com.intcomex.productapi.domain.repository.UserRepository;
import com.intcomex.productapi.web.controller.AuthController.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String username = "testuser";
    private static final String rawPassword = "testpass";

    @BeforeEach
    public void setup() {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = User.builder()
                .id(1L)
                .username(username)
                .password(encodedPassword)
                .roles(Set.of("ROLE_USER"))
                .build();

        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));
    }

    @Test
    public void shouldAuthenticateAndReturnJwtToken() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername(username);
        request.setPassword(rawPassword);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}
