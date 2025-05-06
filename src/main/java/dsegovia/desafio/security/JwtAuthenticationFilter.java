package dsegovia.desafio.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${jwt.header:Authorization}")
    private String tokenHeader;

    @Value("${jwt.prefix:Bearer }")
    private String tokenPrefix;

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        log.info("JWT extraído: {}", jwt);

        if (jwt != null) {
            try {
                if (tokenProvider.validateToken(jwt)) {
                    Authentication auth = tokenProvider.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.info("Autenticación exitosa para usuario: {}", auth.getName());
                }
            } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException |
                     SecurityException | IllegalArgumentException e) {
                log.error("Error de autenticación JWT: {}", e.getMessage());
                SecurityContextHolder.clearContext();
                throw e;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String headerName = (tokenHeader != null) ? tokenHeader : "Authorization";
        String prefix = (tokenPrefix != null) ? tokenPrefix : "Bearer ";

        String bearerToken = request.getHeader(headerName);
        if (bearerToken == null) {
            bearerToken = request.getHeader(headerName.toLowerCase());
        }

        if (bearerToken != null) {
            if (bearerToken.startsWith(prefix)) {
                return bearerToken.substring(prefix.length()).trim();
            }
            return bearerToken.trim();
        }

        return null;
    }
}