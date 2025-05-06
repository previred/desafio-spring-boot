package dsegovia.desafio.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ValidacionErrorException extends RuntimeException {
    private final Map<String, String> errores;

    public ValidacionErrorException(String mensaje) {
        super(mensaje);
        this.errores = new HashMap<>();
    }

    public ValidacionErrorException(String mensaje, Map<String, String> errores) {
        super(mensaje);
        this.errores = errores;
    }

    public void addError(String campo, String mensaje) {
        this.errores.put(campo, mensaje);
    }
}