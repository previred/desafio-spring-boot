package cl.previred.taskmanager.application.usecase;

import cl.previred.taskmanager.domain.model.Task;
import cl.previred.taskmanager.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTaskByIdUseCase {

    private final TaskRepository taskRepository;

    public GetTaskByIdUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task execute(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Tarea no encontrada con id: " + id)
                );
    }
}