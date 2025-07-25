package com.Alsainey.ShimmerShine.security;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        // Manually set the secret key and expiration for testing
        jwtService.jwtExpiration = 1000 * 60 * 60; // 1 hour
        jwtService.secretKey = "6402a36aecb22d888c2257d1c0cdfa53397cf7f35bf2d248cf6e051a3b7068503ea479191dddf2fbffcebd3e239a3f9a8053c4170b918107cbbba0e2a027ef3c"; // Base64 of "SomeSuperSecretKeyForTestingJwt"
    }

    @org.testng.annotations.Test
    void testGenerateTokenAndExtractUsername() {
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);

        String extractedUsername = jwtService.extractUsername(token);
        assertEquals("testuser", extractedUsername);
    }

    @org.testng.annotations.Test
    void testTokenIsValid() {
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());

        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void testTokenIsInvalidForDifferentUser() {
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        UserDetails otherUser = new User("otheruser", "password", Collections.emptyList());

        String token = jwtService.generateToken(userDetails);
        boolean isValid = jwtService.isTokenValid(token, otherUser);

        assertFalse(isValid);
    }

}
