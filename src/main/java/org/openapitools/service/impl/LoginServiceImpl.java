package org.openapitools.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.openapitools.config.AuthProperties;
import org.openapitools.dto.Login;
import org.openapitools.dto.TokenResponse;
import org.openapitools.exceptions.NotFoundException;
import org.openapitools.exceptions.enums.ExceptionEnum;
import org.openapitools.repository.LoginRepository;
import org.openapitools.service.LoginService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;
    private final AuthProperties authProperties;

    public LoginServiceImpl(LoginRepository loginRepository, AuthProperties authProperties) {
        this.loginRepository = loginRepository;
        this.authProperties = authProperties;
    }

    @Override
    public TokenResponse login(Login login) {
        return loginRepository.findByEmailAndPassword(login)
                .map(l -> TokenResponse.builder()
                        .token(generate(l.getEmail()))
                        .build())
                .orElseThrow(() -> new NotFoundException(ExceptionEnum.USER_NOT_FOUND));
    }

    private String generate(String email) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("secret", UUID.randomUUID());
        Claims claims = Jwts.claims(payload).setSubject(email);
        var currentTime = Instant.now();
        Date issuedAt = Timestamp.from(currentTime);
        Instant expiredTime = currentTime.plusSeconds(authProperties.getExpiration());
        Date expiration = Timestamp.from(expiredTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(authProperties.getSecret().getBytes()))
                .compact();
    }
}
