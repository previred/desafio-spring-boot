package cl.previred.desafio.infrastructure.security.exceptions;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(String.format("No se pudo autenticar usuario error: %s", message));
    }
}
