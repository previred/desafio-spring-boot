package cl.previred.taskmanager.domain.model;

import java.time.LocalDateTime;

public record Task(
        Long id,
        String title,
        String description,
        User user,
        TaskStatus status,
        LocalDateTime createdAt
) {

    public Task {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (status == null) {
            status = TaskStatus.PENDING;
        }
    }

}
