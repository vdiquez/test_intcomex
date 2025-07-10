package com.intcomex.productapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intcomex.productapi.domain.model.User;
import com.intcomex.productapi.domain.repository.UserRepository;
import com.intcomex.productapi.web.controller.AuthController.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(com.intcomex.productapi.infrastructure.config.JwtUtil.class)
@Disabled
public class ProductAccessWithJwtTest {

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
    private String jwtToken;

    @BeforeEach
    public void setup() throws Exception {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = User.builder()
                .id(1L)
                .username(username)
                .password(encodedPassword)
                .roles(Set.of("ROLE_USER"))
                .build();

        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));

        AuthRequest request = new AuthRequest();
        request.setUsername(username);
        request.setPassword(rawPassword);

        var loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String json = loginResult.getResponse().getContentAsString();
        jwtToken = objectMapper.readTree(json).get("token").asText();
    }

    @Test
    public void shouldAccessProtectedEndpointWithToken() throws Exception {
        String productRequest = "{\"productName\":\"JWT Test Product\",\"categoryId\":1,\"supplierId\":1,\"unitPrice\":99.99}";

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .content(productRequest))
                .andExpect(status().isOk());
    }
}
