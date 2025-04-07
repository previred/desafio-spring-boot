package cl.previred.desafio.taskmanager.domain.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
    CREATED("created"),
    IN_PROGRESS("in_progress"),
    DONE("done"),
    CANCELLED("cancelled");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public static TaskStatus getByValue(String value) {
        for (TaskStatus currentStatus : TaskStatus.values()) {
            if (currentStatus.status.equalsIgnoreCase(value)) {
                return currentStatus;
            }
        }
        throw new IllegalArgumentException("No existe el estado: " + value);
    }

    @Override
    public String toString() {
        return status;
    }

}
