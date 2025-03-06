package com.provired.challenge.security;

import com.provired.challenge.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractJwt(request);
            if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                validateAndAuthenticate(jwt);
            }
        } catch (Exception e) {
            logger.error("No se pudo autenticar el token JWT: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwt(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
                ? authorizationHeader.substring(7)
                : null;
    }

    private void validateAndAuthenticate(String jwt) {
        String username = jwtUtil.extractUsername(jwt);
        if (username != null) {
            var userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                var authToken = jwtUtil.getAuthenticationToken(jwt, userDetails);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }
}