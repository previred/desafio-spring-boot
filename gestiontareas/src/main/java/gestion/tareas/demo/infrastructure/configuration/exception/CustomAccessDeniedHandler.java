package gestion.tareas.demo.infrastructure.configuration.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUser = (authentication != null) ? authentication.getName() : null;

        JsonErrorResponseUtil.sendError(
                response,
                HttpServletResponse.SC_FORBIDDEN,
                "Acceso denegado: " + accessDeniedException.getMessage(),
                loggedUser
        );
    }
}
