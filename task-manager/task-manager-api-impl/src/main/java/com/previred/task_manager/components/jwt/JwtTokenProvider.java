package com.previred.task_manager.components.jwt;

import com.previred.task_manager.components.exceptions.GeneralException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider implements JwtTokenService{
    @Value("${config.secretKeyJWT}")
    private String secretKeyForJWT;

    @Value("${config.jwtExpiration}")
    private long expirationMillis;
    @Override
    public String generateToken(UUID userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }
    /**
     * Extrae el email del token JWT.
     *
     * @param token El token JWT
     * @return userId del usuario (subject)
     */
    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    /**
     * Convierte la clave secreta (en Base64) a un objeto Key.
     *
     * @return objeto Key a usar para firmar y validar el token
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyForJWT);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Valida el token JWT.
     *
     * @param token El token JWT a validar
     * @return true si es válido, false en caso contrario.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
