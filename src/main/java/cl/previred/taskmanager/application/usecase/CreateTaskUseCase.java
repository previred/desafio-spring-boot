package cl.previred.taskmanager.application.usecase;

import cl.previred.taskmanager.domain.model.Task;
import cl.previred.taskmanager.domain.model.TaskStatus;
import cl.previred.taskmanager.domain.model.User;
import cl.previred.taskmanager.domain.repository.TaskRepository;
import cl.previred.taskmanager.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CreateTaskUseCase(
            TaskRepository taskRepository,
            UserRepository userRepository
    ) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task execute(
            String title,
            String description,
            Long userId,
            String status
    ) {

        // 1️⃣ Validar usuario existente
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe"));

        TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());

        // 2️⃣ Crear objeto de dominio
        Task task = new Task(
                null, // id null porque es nueva
                title,
                description,
                user,
                taskStatus,
                LocalDateTime.now()
        );

        // 3️⃣ Persistir
        return taskRepository.save(task);
    }
}
