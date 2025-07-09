package com.ml.GestionDeTareas.configurations;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title="API SPA",
                description = "Api tareas(crud) y respectiva autenticacion de usuario",
                contact = @Contact(
                        name = "Milagros León",
                        email = "milagrosleonnieves@gmail.com"
                ),
                license = @License(
                        name= "Mi licence",
                        url = "https")),
        servers = @Server(url="http://localhost:8080")
)

public class SwaggerConfiguration {
}
