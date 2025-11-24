package com.api.nuevospa.service;

import com.api.nuevospa.domain.entity.TaskStatus;
import com.api.nuevospa.domain.entity.Task;
import com.api.nuevospa.domain.entity.User;
import com.api.nuevospa.mapper.TaskMapper;
import com.api.nuevospa.model.TaskPatchRequest;
import com.api.nuevospa.model.TaskRequest;
import com.api.nuevospa.model.TaskResponse;
import com.api.nuevospa.repository.TaskStatusRepository;
import com.api.nuevospa.repository.TaskRepository;
import com.api.nuevospa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final UserRepository userRepository;

    public List<TaskResponse> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    public TaskResponse findById(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada"));

        return TaskMapper.toResponse(task);
    }

    public TaskResponse create(TaskRequest taskRequest) {
        TaskStatus taskStatus = taskStatusRepository.findById(taskRequest.getStatusId())
                .orElseThrow(() -> new IllegalArgumentException("Estado inválido"));

        User user = userRepository.findById(taskRequest.getAssignedTo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario inválido"));

        Task task = TaskMapper.toEntity(taskRequest, taskStatus, user);

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse update(Integer id, TaskRequest taskRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada"));

        TaskStatus taskStatus = taskStatusRepository.findById(taskRequest.getStatusId())
                .orElseThrow(() -> new IllegalArgumentException("Estado inválido"));

        User user = userRepository.findById(taskRequest.getAssignedTo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario inválido"));

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskStatus);
        task.setAssignedTo(user);

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse updatePatch(Integer id, TaskPatchRequest taskPatchRequest) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada"));

        if (taskPatchRequest.getTitle() != null)
            task.setTitle(taskPatchRequest.getTitle());

        if (taskPatchRequest.getDescription() != null)
            task.setDescription(taskPatchRequest.getDescription());

        if (taskPatchRequest.getStatusId() != null) {
            TaskStatus taskStatus = taskStatusRepository.findById(taskPatchRequest.getStatusId())
                    .orElseThrow(() -> new IllegalArgumentException("Estado inválido"));
            task.setStatus(taskStatus);
        }

        if (taskPatchRequest.getAssignedTo() != null) {
            User user = userRepository.findById(taskPatchRequest.getAssignedTo())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario inválido"));
            task.setAssignedTo(user);
        }

        return TaskMapper.toResponse(taskRepository.save(task));
    }

    public void delete(Integer id) {
        if (!taskRepository.existsById(id))
            throw new NoSuchElementException("Tarea no encontrada");

        taskRepository.deleteById(id);
    }
}
