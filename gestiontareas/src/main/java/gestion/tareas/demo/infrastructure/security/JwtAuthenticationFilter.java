package gestion.tareas.demo.infrastructure.security;

import gestion.tareas.demo.application.get.usuario.GetUsuarioGateway;
import gestion.tareas.demo.infrastructure.configuration.exception.JsonErrorResponseUtil;
import gestion.tareas.demo.infrastructure.configuration.exception.UnauthorizedException;
import gestion.tareas.demo.infrastructure.db.entity.Usuario;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;


public class JwtAuthenticationFilter implements Filter {

    private static final String SECRET_KEY = "secreto";  // La clave secreta para la firma del token

    private final GetUsuarioGateway getUsuarioGateway;

    public JwtAuthenticationFilter(GetUsuarioGateway getUsuarioGateway) {
        this.getUsuarioGateway = getUsuarioGateway;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        if (path.startsWith("/auth/login")
                || path.startsWith("/swagger")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }

        String loggedUser = null;

        try {
            String token = extractToken(httpRequest);
            if (token == null) {
                throw new UnauthorizedException("Token no proporcionado");
            }

            String username = extractUsernameFromToken(token);
            loggedUser = username;

            Optional<Usuario> usuarioOpt = getUsuarioGateway.findByUsername(username);
            if (usuarioOpt.isEmpty()) {
                throw new UnauthorizedException("Usuario no encontrado");
            }

            String role = usuarioOpt.get().getRole().toUpperCase();
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);

        } catch (JWTVerificationException e) {
            JsonErrorResponseUtil.sendError(httpResponse, HttpServletResponse.SC_UNAUTHORIZED,
                    "Sesión expirada o no autorizado", loggedUser);
        } catch (UnauthorizedException e) {
            JsonErrorResponseUtil.sendError(httpResponse, HttpServletResponse.SC_UNAUTHORIZED,
                    e.getMessage(), loggedUser);
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private String extractUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token).getSubject();
    }
}
