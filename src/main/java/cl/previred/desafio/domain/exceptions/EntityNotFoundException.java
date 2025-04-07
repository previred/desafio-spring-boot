package cl.previred.desafio.domain.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity,String id) {
        super(String.format("%s no encontrada con id: %s",entity,id));
    }
}
