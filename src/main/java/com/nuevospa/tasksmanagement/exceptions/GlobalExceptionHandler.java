package com.nuevospa.tasksmanagement.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Provides centralized error handling for various exception types.
 */
@ControllerAdvice
@Slf4j // Lombok for logging
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException, returning a 404 Not Found status.
     * @param ex ResourceNotFoundException instance
     * @param request WebRequest
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error("Resource not found: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                "NOT_FOUND"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles MethodArgumentNotValidException, returning a 400 Bad Request status.
     * Used for @Valid annotation failures in DTOs.
     * @param ex MethodArgumentNotValidException instance
     * @return ResponseEntity with validation error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles BadCredentialsException, returning a 401 Unauthorized status.
     * Used for authentication failures.
     * @param ex BadCredentialsException instance
     * @param request WebRequest
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        log.warn("Bad credentials provided: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "Invalid username or password", // Generic message for security
                request.getDescription(false),
                "UNAUTHORIZED"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles all other unhandled exceptions, returning a 500 Internal Server Error status.
     * @param ex Exception instance
     * @param request WebRequest
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex); // Log full stack trace for unexpected errors
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                "An unexpected error occurred: " + ex.getMessage(),
                request.getDescription(false),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles DuplicateResourceException.
     * @param ex Exception instance
     * @param request WebRequest
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        log.error("Duplicate resource error: {}", ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                "DUPLICATE_RESOURCE"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Record to encapsulate error details for API responses.
     */
    public record ErrorDetails(
            LocalDateTime timestamp,
            String message,
            String details,
            String errorCode
    ) {}
}
