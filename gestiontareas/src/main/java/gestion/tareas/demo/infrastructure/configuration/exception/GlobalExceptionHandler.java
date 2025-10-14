package gestion.tareas.demo.infrastructure.configuration.exception;

import gestion.tareas.demo.db.StateAppDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private String getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : null;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StateAppDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        StateAppDto response = new StateAppDto();
        response.setLoggedUser(getLoggedUser());
        response.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(response); // 400
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StateAppDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        StateAppDto response = new StateAppDto();
        response.setLoggedUser(getLoggedUser());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<StateAppDto> handleUnauthorizedException(UnauthorizedException ex) {
        StateAppDto response = new StateAppDto();
        response.setLoggedUser(getLoggedUser());
        response.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401
    }
}