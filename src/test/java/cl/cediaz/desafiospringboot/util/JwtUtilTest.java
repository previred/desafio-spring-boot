package cl.cediaz.desafiospringboot.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil();

    @Test
    void testValidToken() {
        String token = jwtUtil.generateToken("testuser");
        UserDetails userDetails = User.withUsername("testuser")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        assertTrue(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testInvalidUsername() {
        String token = jwtUtil.generateToken("testuser");
        UserDetails userDetails = User.withUsername("otheruser")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        assertFalse(jwtUtil.validateToken(token, userDetails));
    }

    @Test
    void testInvalidTokenSignature() {
        String token = jwtUtil.generateToken("testuser");

        String fakeToken = token.substring(0, token.length() - 1) + "x";

        UserDetails userDetails = User.withUsername("testuser")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        assertThrows(RuntimeException.class, () -> jwtUtil.validateToken(fakeToken, userDetails));
    }
}
