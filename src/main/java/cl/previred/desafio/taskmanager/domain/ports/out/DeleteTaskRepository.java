package cl.previred.desafio.taskmanager.domain.ports.out;

public interface DeleteTaskRepository {
    boolean delete(String id);
}
