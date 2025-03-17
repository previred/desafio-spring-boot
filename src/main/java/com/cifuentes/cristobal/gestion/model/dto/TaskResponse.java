package com.cifuentes.cristobal.gestion.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TaskResponse {

	private Long id;
	private String title;
	private String description;
	private String statusName;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<TaskStatusHistoryResponse> history;

	public TaskResponse(Long id, String title, String description, String statusName, LocalDateTime createdAt,
			LocalDateTime updatedAt, List<TaskStatusHistoryResponse> history) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.statusName = statusName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.history = history;
	}

	public TaskResponse() {
		super();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getStatusName() {
		return statusName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public List<TaskStatusHistoryResponse> getHistory() {
		return history;
	}

}
