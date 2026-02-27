package cl.previred.taskmanager.application.usecase;

import cl.previred.taskmanager.domain.model.Task;
import cl.previred.taskmanager.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllTasksUseCase {

    private final TaskRepository taskRepository;

    public GetAllTasksUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> execute() {
        return taskRepository.findAll();
    }
}
