package cl.previred.taskmanager.domain.model;

public record Task(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long userId
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
