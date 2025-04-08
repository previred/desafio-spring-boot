package cl.previred.desafio.taskmanager.domain.usecase;

import cl.previred.desafio.taskmanager.domain.model.Task;

public interface UpdateTaskUseCase {
    Task execute(String id, Task task);
}
