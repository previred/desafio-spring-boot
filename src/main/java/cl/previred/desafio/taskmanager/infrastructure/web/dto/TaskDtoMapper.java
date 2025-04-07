package cl.previred.desafio.taskmanager.infrastructure.web.dto;

import cl.previred.desafio.taskmanager.domain.model.BasicTask;
import cl.previred.desafio.taskmanager.domain.model.Task;
import cl.previred.desafio.taskmanager.domain.model.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TaskDtoMapper {
    public Task toDomain(CreateTaskRequest request) {

        Objects.requireNonNull(request, "Request cannot be null");

        return BasicTask.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .createdBy(request.getCreatedBy())
                .status(TaskStatus.CREATED)
                .build();
    }

    public TaskResponse toResponse(BasicTask task) {

        if (task == null) {
            return null;
        }

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .createdBy(task.getCreatedBy())
                .createdAt(task.getCreated())
                .lastUpdated(task.getLastUpdate())
                .build();
    }
}
