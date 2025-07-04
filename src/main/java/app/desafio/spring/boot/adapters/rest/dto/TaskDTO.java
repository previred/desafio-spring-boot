package app.desafio.spring.boot.adapters.rest.dto;

public record TaskDTO(
        String title,
        String description,
        String status
) {}
