package dsegovia.desafio.security;

import dsegovia.desafio.entity.Rol;
import dsegovia.desafio.entity.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Componente para la generación y validación de tokens JWT
 */
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.prefix}")
    private String tokenPrefix;

    /**
     * Genera un token JWT para el usuario autenticado
     *
     * @param username Nombre de usuario
     * @param roles Roles del usuario
     * @return Token JWT generado
     */
    public String generateToken(String username, Set<Rol> roles) {
        List<String> roleNames = roles.stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(username)
                .claim("roles", roleNames)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Genera un token JWT a partir de un objeto Usuario
     *
     * @param usuario Objeto Usuario
     * @return Token JWT generado
     */
    public String generateToken(Usuario usuario) {
        return generateToken(usuario.getUsername(), usuario.getRoles());
    }

    /**
     * Extrae el nombre de usuario del token JWT
     *
     * @param token Token JWT
     * @return Nombre de usuario
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    /**
     * Valida un token JWT
     *
     * @param token Token JWT a validar
     * @return true si el token es válido
     * @throws JwtException si el token es inválido o ha expirado
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(),
                    "El token ha expirado. Por favor, inicie sesión nuevamente");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Token no soportado");
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("Token con formato incorrecto");
        } catch (SignatureException e) {
            throw new SignatureException("Firma del token inválida");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Token vacío o nulo");
        }
    }

    /**
     * Extrae la autenticación del token JWT
     *
     * @param token Token JWT
     * @return Objeto Authentication
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        @SuppressWarnings("unchecked")
        Collection<? extends GrantedAuthority> authorities =
                ((List<String>) claims.get("roles")).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * Obtiene la clave de firma a partir del secreto configurado
     *
     * @return Clave de firma
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}