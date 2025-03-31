package gestion.tareas.demo.security;

import gestion.tareas.demo.model.Usuario;
import gestion.tareas.demo.service.UsuarioService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

//@Component
//@WebFilter(urlPatterns = "/*")// Esto es opcional dependiendo de cómo registres tu filtro

public class JwtAuthenticationFilter implements Filter {

    private static final String SECRET_KEY = "secreto";  // La clave secreta para la firma del token

    private final UsuarioService usuarioService;

    public JwtAuthenticationFilter(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = extractToken(httpRequest);

        if (token != null && validateToken(token)) {
            String username = extractUsernameFromToken(token);
            String role = "USER";
            if(username!=null){
                Optional<Usuario> existUser = usuarioService.findByUsername(username);
                if(existUser.isPresent()){
                    role = existUser.get().getRole().toUpperCase();
                }
            }
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("Usuario autenticado: " + username);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);  // Extrae el token sin el prefijo "Bearer "
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token);  // Verifica el token
            return true;
        } catch (JWTVerificationException e) {
            return false;  // Si la verificación falla, el token es inválido
        }
    }

    private String extractUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token).getSubject();  // Extrae el usuario del token
    }

}
