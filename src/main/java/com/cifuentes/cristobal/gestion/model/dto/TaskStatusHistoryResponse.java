package com.cifuentes.cristobal.gestion.model.dto;

import java.time.LocalDateTime;

public class TaskStatusHistoryResponse {

    private String statusName;
    private LocalDateTime changedAt;

    public TaskStatusHistoryResponse(String statusName, LocalDateTime changedAt) {
        this.statusName = statusName;
        this.changedAt = changedAt;
    }

    public String getStatusName() {
        return statusName;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }
}