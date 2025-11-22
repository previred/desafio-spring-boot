package com.yoserp1.tareas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AplicacionException.class)
  public ResponseEntity<ErrorResponse> handleAplicacionException(AplicacionException ex) {
    HttpStatus status =
        switch (ex.getCode()) {
          case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
          case "VALIDATION" -> HttpStatus.BAD_REQUEST;
          case "OPERATION_ERROR" -> HttpStatus.INTERNAL_SERVER_ERROR;
          default -> HttpStatus.BAD_REQUEST;
        };

    return ResponseEntity.status(status).body(new ErrorResponse(ex.getCode(), ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage()));
  }
}
