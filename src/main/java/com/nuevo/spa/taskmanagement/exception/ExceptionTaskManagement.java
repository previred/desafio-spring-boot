package com.nuevo.spa.taskmanagement.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice  // Anotación para manejar excepciones globalmente
public class ExceptionTaskManagement {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        // Respuesta con un mensaje más detallado sobre el token expirado
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("El token ha expirado. Asegúrate de obtener un nuevo token.");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException ex) {
        // Respuesta con un mensaje más detallado sobre el token malformado
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("El token no tiene un formato válido. Revisa la estructura del token.");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }
}
