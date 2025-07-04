package app.desafio.spring.boot.domain.usecases;

import app.desafio.spring.boot.adapters.rest.dto.TaskDTO;
import app.desafio.spring.boot.domain.model.Task;
import app.desafio.spring.boot.domain.model.TaskStatus;
import app.desafio.spring.boot.domain.model.User;
import app.desafio.spring.boot.domain.ports.ITaskUseCase;
import app.desafio.spring.boot.domain.service.ISecurityService;
import app.desafio.spring.boot.infrastructure.persistence.ITaskRepository;
import app.desafio.spring.boot.infrastructure.persistence.ITaskStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
final class TaskUseCase implements ITaskUseCase {

    private final ITaskRepository taskRepository;
    private final ITaskStatusRepository taskStatusRepository;
    private final ISecurityService securityService;

    public TaskUseCase(ITaskRepository taskRepository, ITaskStatusRepository taskStatusRepository, ISecurityService securityService) {
        this.taskRepository = taskRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.securityService = securityService;
    }

    @Override
    public Task createTask(String title, String description, String statusName) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setCreatedAt(LocalDateTime.now());

        Optional<TaskStatus> statusOptional = taskStatusRepository.findByStatus(statusName);
        if (statusOptional.isPresent()) {
            task.setStatus(statusOptional.get());
        } else {
            throw new IllegalArgumentException("Invalid task status");
        }

        User currentUser = securityService.getAuthenticatedUser();
        if (currentUser != null) {
            task.setUser(currentUser);
        } else {
            throw new IllegalArgumentException("User not authenticated");
        }

        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        User authenticatedUser = securityService.getAuthenticatedUser();
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("User not authenticated");
        }
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
    }

    @Override
    public List<Task> getAllTasks() {
        User authenticatedUser = securityService.getAuthenticatedUser();
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("User not authenticated");
        }
        return taskRepository.findAll();
    }

    @Override
    public Task updateTask(Long id, TaskDTO taskDTO) {
        User authenticatedUser = securityService.getAuthenticatedUser();
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("User not authenticated");
        }
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));

        existingTask.setTitle(taskDTO.title());
        existingTask.setDescription(taskDTO.description());
        existingTask.setCreatedAt(LocalDateTime.now());

        Optional<TaskStatus> statusOptional = taskStatusRepository.findByStatus(taskDTO.status());
        if (statusOptional.isPresent()) {
            existingTask.setStatus(statusOptional.get());
        } else {
            throw new IllegalArgumentException("Invalid task status");
        }
        existingTask.setUser(authenticatedUser);
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        User authenticatedUser = securityService.getAuthenticatedUser();
        if (authenticatedUser == null) {
            throw new IllegalArgumentException("User not authenticated");
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + id));
        taskRepository.delete(task);
    }
}