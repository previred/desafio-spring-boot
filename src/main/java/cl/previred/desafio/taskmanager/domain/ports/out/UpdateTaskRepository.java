package cl.previred.desafio.taskmanager.domain.ports.out;

import cl.previred.desafio.taskmanager.domain.model.Task;

public interface UpdateTaskRepository {

    Task update(String id, Task task);
}
