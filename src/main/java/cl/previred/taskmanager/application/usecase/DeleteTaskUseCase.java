package cl.previred.taskmanager.application.usecase;

import cl.previred.taskmanager.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(Long id) {

        // Verificar que exista
        taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Tarea no encontrada con id: " + id)
                );

        // Eliminar
        taskRepository.deleteById(id);
    }
}