package com.taskmanagement.dto.response;

public record TaskStateResponse(
    Long id,
    String name,
    String description
) {}
