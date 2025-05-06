package dsegovia.desafio.exception;

public class TokenNoEncontradoException extends RuntimeException {
    public TokenNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}