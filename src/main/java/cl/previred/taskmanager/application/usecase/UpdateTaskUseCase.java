package cl.previred.taskmanager.application.usecase;

import cl.previred.taskmanager.domain.model.Task;
import cl.previred.taskmanager.domain.model.TaskStatus;
import cl.previred.taskmanager.domain.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTaskUseCase {

    private final TaskRepository taskRepository;

    public UpdateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task execute(
            Long id,
            String title,
            String description,
            TaskStatus status
    ) {

        // Buscar existente
        Task existing = taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Tarea no encontrada con id: " + id)
                );

        // Crear nueva instancia actualizada
        Task updated = new Task(
                existing.id(),
                title != null ? title : existing.title(),
                description != null ? description : existing.description(),
                existing.user(),
                status != null ? status : existing.status(),
                existing.createdAt()
        );

        // Persistir
        return taskRepository.save(updated);
    }
}