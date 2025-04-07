package cl.previred.desafio.taskmanager.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BasicTask implements Task{
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private String createdBy;
    private LocalDateTime created;
    private LocalDateTime lastUpdate;

}
