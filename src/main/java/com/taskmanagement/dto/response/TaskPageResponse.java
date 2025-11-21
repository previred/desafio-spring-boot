package com.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TaskPageResponse(
    List<TaskResponse> content,
    int page,
    int size,
    @JsonProperty("totalElements")
    long totalElements,
    @JsonProperty("totalPages")
    int totalPages,
    boolean last
) {}
