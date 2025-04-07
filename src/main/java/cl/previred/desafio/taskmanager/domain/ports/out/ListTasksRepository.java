package cl.previred.desafio.taskmanager.domain.ports.out;

import cl.previred.desafio.taskmanager.domain.model.Task;

import java.util.List;

public interface ListTasksRepository {
    List<Task> retrieve();
}
