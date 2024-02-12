package org.openapitools.datatest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class GeneralContextMock {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET = "SECRET-TEST-FOR-SECURITY-CONTEXT-ON-MOCK-INTEGRATIONS";

    protected Key testKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    protected void mockSecurityContext() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(stubSessionHolder());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    protected SessionHolder stubSessionHolder() {
        return new SessionHolder("test@test.com");
    }

    protected String testToken() {
        SessionHolder stubbed = stubSessionHolder();
        Instant currentTime = Instant.now();
        Date issuedAt = Timestamp.from(currentTime);
        Instant expiredTime = currentTime.plusSeconds(10000L);
        Date expiration = Timestamp.from(expiredTime);
        Claims claims = claims(stubbed);
        claims.put("email", stubbed.email());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(testKey())
                .setHeaderParam("TEST-TOKEN", "JWT-TESTER")
                .compact();
    }

    private Claims claims(SessionHolder sessionHolder) {
        Claims claims = Jwts.claims();
        claims.setSubject(sessionHolder.email());
        return claims;
    }
}
