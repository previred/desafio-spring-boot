package cl.rhoffmann.previred.desafiospringboot.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación Spring Boot para la gestión de tareas en el entorno de desarrollo de NUEVO SPA.
 * 
 * Esta aplicación resuelve el desafío técnico propuesto por PREVIRED, enfocándose en:
 * - **Autenticación de Usuarios**: Implementada mediante JWT para segurar las endpoints.
 * - **CRUD de Tareas**: Funcionalidades completas para crear, leer, actualizar y eliminar tareas,
 *   utilizando JPA sobre una base de datos H2.
 * - **Gestión de Estados de Tarea**: Estados predefinidos en la base de datos H2 para representar
 *   el ciclo de vida de las tareas.
 * - **Documentación de la API**: Utilizando OpenAPI y Swagger para una descripción accesible
 *   y testeable de la API.
 * 
 * Las tecnologías clave utilizadas incluyen:
 * - **Java 17**: Aprovechando características modernas como lambdas y streams.
 * - **Spring Boot 2.7.18**: Para una rápida configuración y despliegue de la aplicación.
 * - **Base de Datos H2**: Para persistencia de datos en memoria, simplificando el desarrollo y las pruebas.
 * 
 * Se sigue una metodología API-First, generando primero el archivo openapi.yml para definir la API,
 * y posteriormente implementando la lógica de negocio conforme a esta especificación.
 * 
 * **Consideraciones de Seguridad**: Se asegura que todas las operaciones CRUD sobre tareas sean accesibles
 * únicamente por usuarios autenticados, protegiendo la integridad y privacidad de los datos. Se incorporada
 * adicionalmente que un usuario solo puede trabajar sobre sus propias tareas . Las tareas se asocian
 * al usuario que las crea y siempre comienzan con el mismo estado inicialmente.
 * 
 * @author Rodolfo Hoffmann Letelier
 * 
 */
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
