package app.desafio.spring.boot.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = UUID.randomUUID().toString();  // Usa una clave secreta generada aleatoriamente

    // Generar el JWT
    public String generateToken(String username) {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Expira en 1 hora
                .signWith(key)
                .compact();
    }

    // Validar el JWT
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    // Obtener el nombre de usuario del token
    public String getUsernameFromToken(String token) {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()); // Clave para validación
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Verificar si el token ha expirado
    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()); // Clave para validación
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
