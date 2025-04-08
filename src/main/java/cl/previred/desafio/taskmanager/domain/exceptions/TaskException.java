package cl.previred.desafio.taskmanager.domain.exceptions;

public class TaskException extends RuntimeException {
    public TaskException(String message) {
        super(message);
    }
}
