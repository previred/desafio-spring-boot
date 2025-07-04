package app.desafio.spring.boot.adapters.rest.response;

import lombok.Data;

@Data
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private String createdAt;  // La fecha formateada como String
    private String status;
    private String username;

    public TaskResponse(Long id, String title, String description, String createdAt, String status, String username) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
        this.username = username;
    }
}
