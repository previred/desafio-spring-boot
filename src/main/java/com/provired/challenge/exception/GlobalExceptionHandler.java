package com.provired.challenge.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ErrorResponse buildErrorResponse(HttpStatus status, String error, String message, String path) {
        return ErrorResponse.of(status.value(), error, message, path);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Mensaje no disponible"
                ));

        ErrorResponse errorResponse = ErrorResponse.withDetails(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                "Errores en los campos de entrada.",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message = ex.getCause() instanceof UnrecognizedPropertyException upe
                ? "Propiedad desconocida: " + upe.getPropertyName()
                : "El cuerpo de la petición no es legible.";

        return ResponseEntity.badRequest().body(
                buildErrorResponse(HttpStatus.BAD_REQUEST, "Error en la petición", message, request.getRequestURI())
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                buildErrorResponse(HttpStatus.FORBIDDEN, "Acceso denegado", "No tienes permisos para acceder.", request.getRequestURI())
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                buildErrorResponse(HttpStatus.NOT_FOUND, "Recurso no encontrado", ex.getMessage(), request.getRequestURI())
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                buildErrorResponse(HttpStatus.CONFLICT, "Conflicto de datos", ex.getMessage(), request.getRequestURI())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                buildErrorResponse(HttpStatus.BAD_REQUEST, "Argumento inválido", ex.getMessage(), request.getRequestURI())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        logger.error("Error interno del servidor", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", "Ha ocurrido un error inesperado.", request.getRequestURI())
        );
    }
}
