package com.previred.task_manager.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.previred.task_manager.components.constants.APIField.ARTEFACT_ID;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI registrationOpenAPI() {
        return new OpenAPI()
                .openapi("3.1.3")
                .info(new Info().title("User Auth API")
                        .description("API para registro de usuarios y generación de token.")
                        .version("v1.0")
                        .contact(new Contact().name("Soporte Técnico").email("rudy.eli.ayala@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación adicional")
                        .url("https://confluence.com/docs/task-manager"));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("task-manager-api-spec")
                .packagesToScan("com.previred.task_manager.controllers", "com.previred.task_manager.dto")
                .build();
    }
}

