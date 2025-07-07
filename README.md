# Task Manager API

[![Java 17](https://img.shields.io/badge/Java-17-blue.svg)](https://www.java.com)
[![Spring Boot 3.5.3](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/build-maven-red.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Una API RESTful segura para la gestión de tareas, construida con Spring Boot. El proyecto sigue los principios de la
arquitectura limpia para garantizar la mantenibilidad, escalabilidad y testeabilidad del código.

## Core Features

- Gestión de Tareas (CRUD): Operaciones completas para crear, leer, actualizar y eliminar tareas.
- Autenticación y Autorización: Seguridad stateless basada en JSON Web Tokens (JWT).
- Gestión de Estado de Tareas: Flujo de estados definido para las tareas (e.g., PENDIENTE, EN PROGRESO, COMPLETADA).
- Documentación de API: Documentación autogenerada e interactiva con OpenAPI 3 (Swagger UI).
- Migraciones de Base de Datos: Gestión de esquema y datos versionada con Liquibase.
- Containerización: Lista para desplegar con Docker y Docker Compose.

## Tech Stack

### Backend

- Framework: Spring Boot 3.5.3
- Lenguaje: Java 17
- Acceso a Datos: Spring Data JPA
- Seguridad: Spring Security, JWT (io.jsonwebtoken)
- Base de Datos: H2 (en memoria para desarrollo/testing), con soporte para cualquier base de datos relacional vía JPA.
- Build Tool: Apache Maven
- Migraciones: Liquibase

### Testing

- Frameworks: JUnit 5, Mockito
- Tests de Integración: spring-boot-starter-test
- Validación Arquitectónica: ArchUnit
- Cobertura de Código: JaCoCo

## Arquitectura

El proyecto sigue una **Arquitectura en Capas (Layered Architecture)**, inspirada en los principios de **Arquitectura
Limpia**, que permite desacoplar la lógica de negocio de los detalles técnicos y facilitar el mantenimiento y
escalabilidad del sistema.

- **`domain`**  
  Contiene las entidades centrales del negocio (`Task`, `User`) y las interfaces de los repositorios. Esta capa es el
  núcleo de la aplicación y no depende de ninguna otra capa.
- **`application`**  
  Implementa los casos de uso del sistema. Aquí se encuentran los servicios de aplicación (`TaskService`,
  `CustomUserDetailsService`), los DTOs que transportan datos entre capas, y los mapeadores.
-
    - **`config`**  
      Agrupa la configuración específica del proyecto, como la seguridad (`SecurityConfig`, `AuthManagerConfig`),
      configuración global de CORS y OpenAPI, codificación de contraseñas, y propiedades externas (`JwtProperties`).
- **`infrastructure`**  
  Contiene componentes relacionados con frameworks o herramientas externas, como los filtros y servicios JWT (
  `JwtAuthenticationFilter`, `JwtService`, `CustomAuthenticationEntryPoint`).
- **`presentation`**  
  Expone la API REST mediante controladores (`TaskController`, `AuthController`). También gestiona las excepciones (
  `GlobalExceptionHandler`) y define los contratos de entrada/salida de la API a través de DTOs.

## Prerrequisitos

- JDK 17
- Apache Maven 3.8+
- Docker & Docker Compose (Opcional, para ejecución containerizada)

## Getting Started

### 1. Clonar el Repositorio

```shell
 git clone <URL_DEL_REPOSITORIO>
 cd task-manager
```

### 2. Ejecutar la Aplicación

La aplicación utiliza la base de datos en memoria H2 por defecto, por lo que no se requiere configuración adicional para
ejecutarla.

```shell
 ./mvnw spring-boot:run
```

Por defecto, la API estará disponible en http://localhost:8080.

### 3. Ejecutar con Docker Compose

Una forma alternativa y aislada de levantar la aplicación es a través de Docker.

```shell
docker-compose up --build
```

## Testing

El proyecto cuenta con un conjunto completo de tests unitarios, de integración y de arquitectura. Para ejecutar todos
los tests y
generar el informe de cobertura:

```shell
 ./mvnw clean verify
```

- Los tests se ejecutan con el plugin Maven Surefire.
- El informe de cobertura de JaCoCo se genera en `target/site/jacoco/index.html`.

## API Documentation (Swagger)

Una vez que la aplicación está en ejecución, puedes acceder a la documentación interactiva de la API a través de Swagger
UI en la
siguiente URL:

http://localhost:8080/swagger-ui.html

Aunque este proyecto fue inicialmente desarrollado con un enfoque Code First, se ha consolidado un contrato OpenAPI
completo y válido, que puede ser utilizado como base para:

- Validar la compatibilidad futura con clientes.
- Generar SDKs o clientes REST automáticamente (openapi-generator).
- Adoptar una estrategia API First real en siguientes iteraciones.

Puedes encontrar el archivo generado (openapi.yaml) dentro de src/main/resources/ para propósitos de documentación y
validación de requisitos del desafío técnico.

## Endpoints principales:

* `POST /auth/login` – **Abierto**. Devuelve un token JWT.
* `GET /tasks` – **Protegido**. Lista tareas del usuario autenticado.
* `POST /tasks` – **Protegido**. Crea una nueva tarea.
* `PUT /tasks/{id}` – **Protegido**. Actualiza una tarea existente.
* `DELETE /tasks/{id}` – **Protegido**. Elimina una tarea.
* `GET /tasks/filter` – **Protegido**. Filtra tareas por título, estado y fecha de vencimiento.

## Database & Migrations

El modelo relacional se basa en 3 entidades: `users`, `tasks` y `task_status`, con relaciones 1:N entre usuarios-tareas
y estados-tareas. Las tablas `databasechangelog` y `databasechangeloglock` son utilizadas por Liquibase.
[![](https://mermaid.ink/img/pako:eNqNU8tu2zAQ_BWCQC6pI9h6Rdahl6a33NpTIcBYkxuZsUQqfPQRw1_VT-iPdSXZqpUGQXjRcrizM7ukDlwYibzkogHn7hTUFtpKM1pXV-wrbAlmEhl0jRIg1J_fejwd8llwaB07jFC_PtwbXTMlL5Av3irC-lQNLf5_0lGlH8aeOMdqpuDB7TfOgw_v1Pmn8Uqld9bwyjevGJXohFWdV0bPqgho7sAjkwE3koKXEqP9zUxpOOhnMsHH8XM2PY624quKs5ubjxQto-iaNmMfJaOeVK1RMm9GxuWo3uCdMszDWWl20cJob00zhNBulRnQe_UU1BYcXg6UWoUeEzvQNTamPg83iqKxk9PmxXWQ3mcyQYZIlKm2U6TwFJA5ZPiIInjUTJKw0pSmhYK5BwaMeN_x-U0zjRH76bYnExd2-ILXVklePkDjcMFbtC30ez6wKu53SE-JlxRKsPuKV7ondaC_GdPy0ttANGtCvZuKhK5_AKf_aEItaon2kwna8zK-HWrw8sB_8nJVZFGW5Em-ytdpkmTrdMF_ERyvoyxbFXmax-kyTtL0uODPg-wyyop8WSTpOk6TPC_y4vgXQ_Ik8A?type=png)](https://mermaid.live/edit#pako:eNqNU8tu2zAQ_BWCQC6pI9h6Rdahl6a33NpTIcBYkxuZsUQqfPQRw1_VT-iPdSXZqpUGQXjRcrizM7ukDlwYibzkogHn7hTUFtpKM1pXV-wrbAlmEhl0jRIg1J_fejwd8llwaB07jFC_PtwbXTMlL5Av3irC-lQNLf5_0lGlH8aeOMdqpuDB7TfOgw_v1Pmn8Uqld9bwyjevGJXohFWdV0bPqgho7sAjkwE3koKXEqP9zUxpOOhnMsHH8XM2PY624quKs5ubjxQto-iaNmMfJaOeVK1RMm9GxuWo3uCdMszDWWl20cJob00zhNBulRnQe_UU1BYcXg6UWoUeEzvQNTamPg83iqKxk9PmxXWQ3mcyQYZIlKm2U6TwFJA5ZPiIInjUTJKw0pSmhYK5BwaMeN_x-U0zjRH76bYnExd2-ILXVklePkDjcMFbtC30ez6wKu53SE-JlxRKsPuKV7ondaC_GdPy0ttANGtCvZuKhK5_AKf_aEItaon2kwna8zK-HWrw8sB_8nJVZFGW5Em-ytdpkmTrdMF_ERyvoyxbFXmax-kyTtL0uODPg-wyyop8WSTpOk6TPC_y4vgXQ_Ik8A)

Las migraciones de base de datos se gestionan con **Liquibase**.

* Los scripts se encuentran en: `src/main/resources/db/changelog`
* **Liquibase** se ejecuta automáticamente al iniciar la aplicación y aplica los cambios necesarios al esquema.

## Postman

Se incluye una colección Postman con ejemplos de pruebas para la API.

- Colección: [Task Manager API.postman_collection.json](Task%20Manager%20API.postman_collection.json)
Se puede importar, para probar los endpoints rapidamente
- 
## TODO: Mejoras futuras

Estas son algunas funcionalidades y mejoras que podría implementar en un futuro para escalar y robustecer el sistema:

### Seguridad

- Soporte para roles de usuario (ADMIN, USER) con control de acceso.
- Soporte para refresh tokens.
- Delegar acceso y crear un endpoint para el registro de usuarios a un microservicio de identidad/autenticación para mantener este microservicio enfocado en la gestión de tareas y evitar acoplamiento innecesario.

### Notificaciones

- Integrar un sistema de mensajería (ej. RabbitMQ, Kafka) para enviar notificaciones al completar tarea

### Escalabilidad
- Migrar de H2 a una base de datos persistente (PostgreSQL, MySQL).
- Pensando en una escalada de del ambiente (con gw, más microservicios):
  - Métricas con Micrometer y exponer endpoint `/actuator/metrics` para integración con Prometheus o servicios de monitoreo.
  - Incorporar patrones de resiliencia (Retry, Circuit Breaker) usando Resilience4j.