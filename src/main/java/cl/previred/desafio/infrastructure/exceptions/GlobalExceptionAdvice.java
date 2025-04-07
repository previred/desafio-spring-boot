package cl.previred.desafio.infrastructure.exceptions;

import cl.previred.desafio.domain.exceptions.EntityNotFoundException;
import cl.previred.desafio.domain.exceptions.UsernameNotFoundException;
import cl.previred.desafio.infrastructure.exceptions.catalog.CatalogException;
import cl.previred.desafio.infrastructure.exceptions.dto.ExceptionResponse;
import cl.previred.desafio.infrastructure.security.exceptions.InvalidLoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse>handleEntityNotFoundException(EntityNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                CatalogException.ENTITY_NOT_FOUND.getCode(),
                CatalogException.ENTITY_NOT_FOUND.getMessage(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse>handleUsernameNotFoundException(UsernameNotFoundException exception) {
        ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                CatalogException.USERNAME_NOT_FOUND.getCode(),
                CatalogException.USERNAME_NOT_FOUND.getMessage(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ExceptionResponse>handleInvalidLoginException(InvalidLoginException exception) {
        ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                CatalogException.INVALID_LOGIN.getCode(),
                CatalogException.INVALID_LOGIN.getMessage(),
                exception.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String bodyError= exception.getFieldError().getField() + ": " + exception.getFieldError().getDefaultMessage();
        ExceptionResponse response = new ExceptionResponse(
                LocalDateTime.now(),
                CatalogException.VALIDATION_REQUEST.getCode(),
                CatalogException.VALIDATION_REQUEST.getMessage(),
                bodyError
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
