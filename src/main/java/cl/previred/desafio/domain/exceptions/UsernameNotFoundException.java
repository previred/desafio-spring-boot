package cl.previred.desafio.domain.exceptions;

public class UsernameNotFoundException extends RuntimeException {
  public UsernameNotFoundException(String username) {
    super(String.format("Usuario con username '%s' no encontrado", username));
  }
}
