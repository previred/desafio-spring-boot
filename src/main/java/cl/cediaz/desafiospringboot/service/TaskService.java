package cl.cediaz.desafiospringboot.service;

import cl.cediaz.desafiospringboot.dto.TaskRequest;
import cl.cediaz.desafiospringboot.dto.TaskResponse;
import cl.cediaz.desafiospringboot.entity.Task;
import cl.cediaz.desafiospringboot.entity.TaskStatus;
import cl.cediaz.desafiospringboot.entity.User;
import cl.cediaz.desafiospringboot.exception.ResourceNotFoundException;
import cl.cediaz.desafiospringboot.mapper.TaskMapper;
import cl.cediaz.desafiospringboot.repository.TaskRepository;
import cl.cediaz.desafiospringboot.repository.TaskStatusRepository;
import cl.cediaz.desafiospringboot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper, TaskStatusRepository taskStatusRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
        this.taskStatusRepository = taskStatusRepository;
    }

    public List<TaskResponse> findByUserId(Long userId) {
        return taskRepository.findByUsers_Id(userId).stream()
                .map(taskMapper::toResponse)
                .toList();
    }


    public TaskResponse findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea con id " + id + " no encontrada"));
        return taskMapper.toResponse(task);
    }

    public TaskResponse save(TaskRequest request) {
        TaskStatus status = taskStatusRepository.findById(request.statusId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de tarea no encontrado"));

        List<User> users = userRepository.findAllById(request.userIds());

        Task task = taskMapper.toEntity(request, status, users);

        Task saved = taskRepository.save(task);

        return taskMapper.toResponse(saved);
    }


    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, tarea con id " + id + " no existe");
        }
        taskRepository.deleteById(id);
    }

    public TaskResponse assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea con id " + taskId + " no encontrada"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        task.getUsers().add(user);
        user.getTasks().add(task);

        userRepository.save(user);
        Task updated = taskRepository.save(task);
        return taskMapper.toResponse(updated);
    }

    public List<TaskResponse> findAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toResponse)
                .toList();
    }


    public TaskResponse update(Long id, TaskRequest request) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea con id " + id + " no encontrada"));

        TaskStatus status = taskStatusRepository.findById(request.statusId())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de tarea no encontrado"));

        List<User> users = userRepository.findAllById(request.userIds());

        existingTask.setTitle(request.title());
        existingTask.setDescription(request.description());
        existingTask.setDueDate(request.dueDate());
        existingTask.setStatus(status);
        existingTask.setUsers(users);

        Task updated = taskRepository.save(existingTask);
        return taskMapper.toResponse(updated);
    }

}