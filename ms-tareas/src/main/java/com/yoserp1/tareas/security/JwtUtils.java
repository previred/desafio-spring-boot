package com.yoserp1.tareas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtUtils {

  private final JwtProperties jwtProperties;

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    Instant now = Instant.now();
    Instant expiry = now.plus(jwtProperties.getExpiration(), ChronoUnit.SECONDS);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expiry))
        .signWith(getSigningKey())
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = parseToken(token);
    return claimsResolver.apply(claims);
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public boolean isTokenExpired(String token) {
    final Date expiration = extractClaim(token, Claims::getExpiration);
    return expiration.before(new Date());
  }

  private Claims parseToken(String token) {
    try {
      return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    } catch (JwtException | IllegalArgumentException e) {
      throw new RuntimeException("Token JWT inválido o expirado", e);
    }
  }
}
