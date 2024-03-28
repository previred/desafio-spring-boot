package cl.rhoffmann.previred.desafiospringboot.api.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cl.rhoffmann.previred.desafiospringboot.api.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


/**
 * Utilidad para la generación y validación de tokens JWT para autenticación de usuarios.
 * <p>
 * Proporciona métodos para generar un nuevo token de acceso para un usuario, validar la legitimidad y
 * la no expiración de un token proporcionado, y extraer información del token como el subject.
 * </p>
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@Component
public class JwtTokenUtil {
	
	private static Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
	
	private static final long EXPIRE_DURATION = 24l * 60 * 60 * 1000; 
    
    @Value("${app.jwt.secret}")
    private String secretKey;
     
    /**
     * Genera un token JWT para un usuario dado.
     *
     * @param usuario El usuario para el cual generar el token.
     * @return Un String representando el token JWT.
     */
    public String generarTokenAcceso(Usuario usuario) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", usuario.getId(), usuario.getCorreo()))
                .setIssuer("Previred")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
                 
    }
    
    /**
     * Valida si el token de acceso proporcionado es legítimo y no ha expirado.
     *
     * @param token El token JWT a validar.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean validarTokenAcceso(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("JWT expirado", ex);
        } catch (IllegalArgumentException ex) {
        	log.error("Token es nulo o vacio", ex);
        } catch (MalformedJwtException ex) {
        	log.error("JWT invalido", ex);
        } catch (UnsupportedJwtException ex) {
        	log.error("JWT no soportado", ex);
        } catch (SignatureException ex) {
        	log.error("Falla en validacion de firma");
        }
         
        return false;
    }
     
    /**
     * Extrae el subject (sujeto) del token JWT.
     *
     * @param token El token JWT del cual extraer la información.
     * @return El subject del token.
     */
    public String obtenerSubject(String token) {
        return obtenerClaims(token).getSubject();
    }
     
    /**
     * Extrae las reivindicaciones (claims) de un token JWT.
     *
     * @param token El token del cual extraer las claims.
     * @return Un objeto Claims que contiene todas las reivindicaciones del token.
     */
    private Claims obtenerClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}