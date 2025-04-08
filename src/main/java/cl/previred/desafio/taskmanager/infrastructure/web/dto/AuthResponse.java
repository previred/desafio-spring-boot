package cl.previred.desafio.taskmanager.infrastructure.web.dto;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {}
