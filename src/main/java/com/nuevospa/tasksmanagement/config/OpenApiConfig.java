package com.nuevospa.tasksmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI documentation (Swagger UI).
 * Defines API metadata, security schemes, and default security requirements.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Task Management API",
                version = "1.0",
                description = "API for managing tasks and users for NUEVO SPA teams.",
                contact = @Contact(name = "Jose Alfredo Ramos", email = "josealfredoramos1234@gmail.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080/api/v1", description = "Local Development Server")
        },
        security = @SecurityRequirement(name = "bearerAuth") // Applies this security scheme globally
)
@SecurityScheme(
        name = "bearerAuth", // This name must match the one used in @SecurityRequirement
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "JWT authentication token (e.g., 'Bearer your_token_here')"
)
public class OpenApiConfig {
        // Configuration handled by annotations
}