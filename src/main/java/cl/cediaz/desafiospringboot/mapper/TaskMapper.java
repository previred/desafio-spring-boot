package cl.cediaz.desafiospringboot.mapper;

import cl.cediaz.desafiospringboot.dto.TaskRequest;
import cl.cediaz.desafiospringboot.dto.TaskResponse;
import cl.cediaz.desafiospringboot.entity.Task;
import cl.cediaz.desafiospringboot.entity.TaskStatus;
import cl.cediaz.desafiospringboot.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getDueDate(),
                task.getStatus().getName(),
                task.getUsers().stream()
                        .map(User::getId)
                        .collect(Collectors.toList())
        );
    }

    public List<TaskResponse> toResponseList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Task toEntity(TaskRequest dto, TaskStatus status, List<User> users) {
        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setDueDate(dto.dueDate());
        task.setStatus(status);
        task.setUsers(users);
        task.setCreatedAt(LocalDateTime.now());
        return task;
    }
}
