package app.desafio.spring.boot.domain.ports;

import app.desafio.spring.boot.adapters.rest.dto.TaskDTO;
import app.desafio.spring.boot.domain.model.Task;

import java.util.List;

public interface ITaskUseCase {
    Task createTask(String title, String description, String statusName);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    Task updateTask(Long id, TaskDTO task);
    void deleteTask(Long id);
}
