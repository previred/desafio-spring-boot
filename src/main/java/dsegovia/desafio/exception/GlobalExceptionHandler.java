package dsegovia.desafio.exception;

import dsegovia.desafio.dto.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Genera un ID único para cada solicitud de error
     */
    private String generarRequestId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Maneja excepciones de tipo RecursoNoEncontradoException
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRecursoNoEncontradoException(
            RecursoNoEncontradoException ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error: recurso no encontrado. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI(),
                requestId
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones de tipo AccesoDenegadoException y AccessDeniedException de Spring Security
     */
    @ExceptionHandler({AccesoDenegadoException.class, AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAccesoDenegadoException(
            Exception ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error: acceso denegado. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.FORBIDDEN,
                ex.getMessage(),
                request.getRequestURI(),
                requestId
        );

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Maneja excepciones de validación de datos (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error: validación de datos. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                "Error de validación",
                request.getRequestURI(),
                requestId
        );

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones personalizadas de validación
     */
    @ExceptionHandler(ValidacionErrorException.class)
    public ResponseEntity<ErrorResponse> handleValidacionErrorException(
            ValidacionErrorException ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error: validación personalizada. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI(),
                requestId
        );

        // Agregar todos los errores personalizados
        if (ex.getErrores() != null) {
            ex.getErrores().forEach(error::addValidationError);
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de negocio
     */
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErrorResponse> handleNegocioException(
            NegocioException ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error de negocio. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI(),
                requestId
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de autenticación
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(
            BadCredentialsException ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error: credenciales inválidas. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.UNAUTHORIZED,
                "Credenciales incorrectas",
                request.getRequestURI(),
                requestId
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja casos donde no se encuentra un controlador para la URL solicitada
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error: endpoint no encontrado. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.NOT_FOUND,
                "El recurso solicitado no existe",
                request.getRequestURI(),
                requestId
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja cualquier excepción no contemplada anteriormente
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error interno del servidor. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor",
                request.getRequestURI(),
                requestId
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja errores de token JWT inválido
     */
    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleTokenInvalidoException(
            TokenInvalidoException ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error: token JWT inválido. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.UNAUTHORIZED,
                "Token de autenticación inválido",
                request.getRequestURI(),
                requestId
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja errores de token JWT expirado
     */
    @ExceptionHandler(TokenVencidoException.class)
    public ResponseEntity<ErrorResponse> handleTokenVencidoException(
            TokenVencidoException ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error: token JWT vencido. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.UNAUTHORIZED,
                "La sesión ha expirado, por favor inicie sesión nuevamente",
                request.getRequestURI(),
                requestId
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja errores de token JWT no encontrado
     */
    @ExceptionHandler(TokenNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleTokenNoEncontradoException(
            TokenNoEncontradoException ex, HttpServletRequest request) {

        String requestId = generarRequestId();
        log.error("Error: token JWT no encontrado. RequestId: {}", requestId, ex);

        ErrorResponse error = ErrorResponse.of(
                HttpStatus.UNAUTHORIZED,
                "Se requiere autenticación para acceder a este recurso",
                request.getRequestURI(),
                requestId
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}