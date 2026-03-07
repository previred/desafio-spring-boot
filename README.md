🚀 Desafío Técnico: Gestión de Tareas - Nuevo SPA
Este proyecto es una API RESTful robusta diseñada para la gestión de equipos de productividad. Implementa un sistema completo de tareas (CRUD) con seguridad perimetral mediante JWT y una arquitectura basada en el contrato OpenAPI 3.

🛠️ Stack Tecnológico
Java 21: Uso intensivo de Records, Streams y Sealed Classes para un código moderno y seguro.

Spring Boot 3.5.x: Aprovechando las últimas optimizaciones del framework.

Security & JWT: Autenticación stateless con filtros personalizados de seguridad.

Persistencia: JPA / Hibernate sobre base de datos H2 en memoria.

Documentación & API First: Contrato definido en openapi.yml con generación automática de interfaces vía Maven.

🏗️ Arquitectura y Buenas Prácticas
SOLID: Responsabilidades segregadas (Controladores para tráfico, Servicios para lógica, Repositorios para datos).

Manejo de Excepciones: Implementación de un GlobalExceptionHandler para capturar errores de validación, tipos de datos incorrectos y accesos no autorizados.

Seguridad de Datos: Uso de BCrypt para el almacenamiento de credenciales, asegurando que ninguna contraseña resida en texto plano en la BD.

🚦 Guía de Inicio Rápido
Requisitos Previos
JDK 21 instalado.

Maven 3.8+ (o usar el wrapper ./mvnw incluido).

Ejecución
Clonar el repositorio.

Desde la raíz, ejecutar el comando:

Bash
./mvnw spring-boot:run
La aplicación estará disponible en http://localhost:8080.

🔑 Autenticación y Datos de Prueba
La base de datos se inicializa automáticamente con:

Usuario: admin_previred

Password: admin123

Estados: PENDIENTE, EN_PROGRESO, COMPLETADA.

Ejemplo de Login (cURL)
Bash
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"username": "admin_previred", "password": "admin123"}'
📝 Documentación de la API
Para explorar los endpoints y probar la interactividad:

Swagger UI: http://localhost:8080/swagger-ui/index.html

Consola H2: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:previred_db

User: sa | Pass: password

👨‍💻 Información del Postulante
Nombre: Julio Muñoz Cortés

Correo: julio.mun.cor@gmail.com

Cargo: Desarrollador Senior Backend