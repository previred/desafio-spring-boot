package cl.previred.desafio.infrastructure.security.filters;

import cl.previred.desafio.infrastructure.security.rest.dtos.InvalidAuthResponse;
import cl.previred.desafio.infrastructure.security.filters.utils.JwtProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;


public class JwtValidFilter extends BasicAuthenticationFilter {

    public JwtValidFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(JwtProperties.PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.replace(JwtProperties.PREFIX, "");
        try {
            Claims claims = Jwts.parser().verifyWith(JwtProperties.JWT_KEY).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            String role = claims.get("role").toString();
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.singleton(new SimpleGrantedAuthority(role))
                    );
            SecurityContextHolder.getContext().setAuthentication(auth);
            chain.doFilter(request, response);

        } catch (Exception e) {
            response.getWriter().write(new ObjectMapper().writeValueAsString(new InvalidAuthResponse(e.getMessage())));
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        }
    }
}
