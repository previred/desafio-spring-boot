package cl.previred.desafio.taskmanager.infrastructure.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateTaskRequest {
    private String title;
    private String description;
    private String status;
}
