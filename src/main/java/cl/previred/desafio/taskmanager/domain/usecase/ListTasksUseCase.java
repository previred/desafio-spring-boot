package cl.previred.desafio.taskmanager.domain.usecase;

import cl.previred.desafio.taskmanager.domain.model.Task;

import java.util.List;

public interface ListTasksUseCase {
    List<Task> execute();
}
