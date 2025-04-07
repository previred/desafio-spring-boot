package cl.previred.desafio.taskmanager.domain.ports.out;

import cl.previred.desafio.taskmanager.domain.model.Task;

public interface CreateTaskRepository {
    Task save(Task task);
}
