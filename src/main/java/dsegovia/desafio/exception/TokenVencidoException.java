package dsegovia.desafio.exception;

public class TokenVencidoException extends RuntimeException {
    public TokenVencidoException(String mensaje) {
        super(mensaje);
    }
}