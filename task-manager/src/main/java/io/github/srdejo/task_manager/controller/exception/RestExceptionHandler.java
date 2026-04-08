package io.github.srdejo.task_manager.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(ResourceNotFoundException ex) {
        return new ApiError(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleUnauthorized(UnauthorizedException ex) {
        return new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Solicitud inválida",
                details
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(IllegalArgumentException ex) {
        return new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGeneric(Exception ex) {
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                null
        );
    }
}
