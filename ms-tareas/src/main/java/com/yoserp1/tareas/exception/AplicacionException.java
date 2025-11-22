package com.yoserp1.tareas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AplicacionException extends RuntimeException {
  private final String code;

  private AplicacionException(String code, String message) {
    super(message);
    this.code = code;
  }

  private AplicacionException(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public static AplicacionException notFound(String recurso, Object id) {
    return new AplicacionException("NOT_FOUND", recurso + " no encontrado: " + id);
  }

  public static AplicacionException validation(String message) {
    return new AplicacionException("VALIDATION", message);
  }

  public static AplicacionException operation(String message, Throwable cause) {
    return new AplicacionException("OPERATION_ERROR", message, cause);
  }

  public static AplicacionException operation(String message) {
    return new AplicacionException("OPERATION_ERROR", message);
  }
}
