package dsegovia.desafio.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dsegovia.desafio.dto.error.ErrorResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtExceptionHandlerFilter.class);
    private final ObjectMapper objectMapper;

    public JwtExceptionHandlerFilter() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("JWT expirado: {}", e.getMessage());
            sendErrorResponse(response,
                    "El token ha expirado. Por favor, inicie sesión nuevamente",
                    request.getRequestURI());
        } catch (MalformedJwtException e) {
            log.error("JWT malformado: {}", e.getMessage());
            sendErrorResponse(response,
                    "Token con formato incorrecto",
                    request.getRequestURI());
        } catch (UnsupportedJwtException e) {
            log.error("JWT no soportado: {}", e.getMessage());
            sendErrorResponse(response,
                    "Token no soportado",
                    request.getRequestURI());
        } catch (SecurityException e) {
            log.error("Error de seguridad JWT: {}", e.getMessage());
            sendErrorResponse(response,
                    "Firma del token inválida",
                    request.getRequestURI());
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Error JWT: {}", e.getMessage());
            sendErrorResponse(response,
                    "Error de autenticación: " + e.getMessage(),
                    request.getRequestURI());
        }
    }

    private void sendErrorResponse(HttpServletResponse response,
                                   String message, String path) throws IOException {
        String requestId = UUID.randomUUID().toString();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(message)
                .path(path)
                .requestId(requestId)
                .build();

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}