package cl.previred.desafio.taskmanager.domain.usecase;

import cl.previred.desafio.taskmanager.domain.model.Task;

public interface CreateTaskUseCase {
    Task execute(Task task);
}
