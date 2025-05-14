package com.nuevospa.gestiontareas.service;

import com.nuevospa.gestiontareas.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtEncoder jwtEncoder;

    public String generateToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getUsername())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .claim("role", user.getRol())
                .build();

        JwtEncoderParameters safeParams = JwtEncoderParameters.from(
                org.springframework.security.oauth2.jwt.JwsHeader.with(() -> "HS256").build(),
                claims
        );
        return jwtEncoder.encode(safeParams).getTokenValue();
    }
}