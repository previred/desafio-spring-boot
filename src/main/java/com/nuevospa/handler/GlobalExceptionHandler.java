package com.nuevospa.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nuevospa.exception.CamposRequeridosVaciosException;
import com.nuevospa.exception.CredencialesInvalidasException;
import com.nuevospa.exception.ErrorInesperadoException;
import com.nuevospa.exception.RecursoNoEncontradoException;
import com.nuevospa.model.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ErrorInesperadoException.class)
    public ResponseEntity<ErrorDto> handleErrorInesperado() {
        ErrorDto error = new ErrorDto();
        error.setCodigo("ERR000");
        error.setMensaje("Ha ocurrido un error inesperado.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ErrorDto> handleCredencialesInvalidas() {
        ErrorDto error = new ErrorDto();
        error.setCodigo("ERR001");
        error.setMensaje("Correo o contraseña inválida.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(CamposRequeridosVaciosException.class)
    public ResponseEntity<ErrorDto> handleCamposRequeridosVacios() {
        ErrorDto error = new ErrorDto();
        error.setCodigo("ERR002");
        error.setMensaje("Campos requeridos vacios.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
        public ResponseEntity<ErrorDto> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
            ErrorDto error = new ErrorDto();
            error.setCodigo("ERR003");
            error.setMensaje(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
}
