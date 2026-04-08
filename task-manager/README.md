# Task Manager API - Guía de Ejecución y Uso

API RESTful para gestión de tareas con autenticación JWT, construida con Spring Boot 3.5.x y Java 21.

## Tabla de Contenidos
- [Requisitos Previos](#requisitos-previos)
- [Ejecución de la Aplicación](#ejecución-de-la-aplicación)
- [Acceso a la Documentación](#acceso-a-la-documentación)
- [Autenticación](#autenticación)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Arquitectura](#arquitectura)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)

---

## Requisitos Previos

- **Java 21** o superior
- **Maven 3.6+**
- Acceso a terminal/CMD

### Versiones de dependencias clave
- Spring Boot 3.5.14-SNAPSHOT
- Spring Security + JWT (JJWT 0.13.0)
- H2 Database (en memoria)
- SpringDoc OpenAPI 2.8.16

---

## Ejecución de la Aplicación

### 1. Compilar el proyecto
```bash
cd task-manager
mvn clean install
```

Esta ejecución también genera automáticamente las interfaces API desde `openapi.yml` usando el plugin `openapi-generator-maven-plugin`.

### 2. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en:
```
http://localhost:8080
```

### 3. Acceso a la Consola H2 (Base de Datos)
```
http://localhost:8080/h2-console
```

**Credenciales H2:**
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (vacío)

---

## Acceso a la Documentación

### OpenAPI/Swagger UI
```
http://localhost:8080/swagger-ui.html
```

La documentación interactiva permite:
- Ver todos los endpoints disponibles
- Probar las APIs directamente desde el navegador
- Autenticarse con JWT
- Ver esquemas de request/response

### Archivo OpenAPI YAML
```
http://localhost:8080/openapi.yml
```

---

## Autenticación

Todos los endpoints de tareas (`/tasks/**`) requieren autenticación mediante JWT. El token se obtiene del endpoint `/auth/login`.

### Credenciales de Prueba
```
username: admin
password: admin123
```

> **Nota:** La contraseña en la BD está almacenada con hash BCrypt.

### Flujo de Autenticación
1. Hacer POST a `/auth/login` con credenciales
2. Recibir token JWT en la response
3. Incluir el token en el header `Authorization: Bearer <token>` para acceder a endpoints protegidos
4. Token válido por 24 horas

---

## Ejemplos de Uso

### 1. Autenticación (Obtener JWT)

**Request:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0NDMyMTYwMCwiZXhwIjoxNjQ0NDA4MDAwfQ...."
}
```

---

### 2. Listar Tareas

**Request:**
```bash
curl -X GET http://localhost:8080/tasks \
  -H "Authorization: Bearer <tu_token_aqui>"
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Implementar login",
    "descripcion": "Crear endpoint de autenticación con JWT",
    "estado": "COMPLETADA",
    "usuario": "admin"
  },
  {
    "id": 2,
    "titulo": "Documentar API",
    "descripcion": "Crear documentación OpenAPI",
    "estado": "EN_PROGRESO",
    "usuario": "admin"
  }
]
```

---

### 3. Crear Tarea

**Request:**
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <tu_token_aqui>" \
  -d '{
    "titulo": "Nueva tarea",
    "descripcion": "Descripción de la nueva tarea",
    "estadoId": 1
  }'
```

**Estados disponibles:**
- `1` = PENDIENTE
- `2` = EN_PROGRESO
- `3` = COMPLETADA

**Response (200 OK):**
```json
{
  "id": 3,
  "titulo": "Nueva tarea",
  "descripcion": "Descripción de la nueva tarea",
  "estado": "PENDIENTE",
  "usuario": "admin"
}
```

---

### 4. Obtener Tarea por ID

**Request:**
```bash
curl -X GET http://localhost:8080/tasks/1 \
  -H "Authorization: Bearer <tu_token_aqui>"
```

**Response (200 OK):**
```json
{
  "id": 1,
  "titulo": "Implementar login",
  "descripcion": "Crear endpoint de autenticación con JWT",
  "estado": "COMPLETADA",
  "usuario": "admin"
}
```

---

### 5. Actualizar Tarea

**Request:**
```bash
curl -X PUT http://localhost:8080/tasks/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <tu_token_aqui>" \
  -d '{
    "titulo": "Actualizar login",
    "descripcion": "Mejorar endpoint de autenticación",
    "estadoId": 2
  }'
```

**Response (200 OK):**
```json
{
  "id": 1,
  "titulo": "Actualizar login",
  "descripcion": "Mejorar endpoint de autenticación",
  "estado": "EN_PROGRESO",
  "usuario": "admin"
}
```

---

### 6. Eliminar Tarea

**Request:**
```bash
curl -X DELETE http://localhost:8080/tasks/1 \
  -H "Authorization: Bearer <tu_token_aqui>"
```

**Response (204 No Content):**
```
(sin body)
```

---

## Manejo de Errores

### 400 - Bad Request
```json
{
  "status": 400,
  "message": "Solicitud inválida",
  "details": [
    "titulo: no debe estar vacío",
    "estadoId: no puede ser nulo"
  ]
}
```

### 401 - Unauthorized
```json
{
  "status": 401,
  "message": "Credenciales inválidas"
}
```

### 404 - Not Found
```json
{
  "status": 404,
  "message": "Tarea no encontrada"
}
```

### 500 - Internal Server Error
```json
{
  "status": 500,
  "message": "Error interno del servidor"
}
```

---

## Estructura del Proyecto

```
task-manager/
├── src/
│   ├── main/
│   │   ├── java/io/github/srdejo/task_manager/
│   │   │   ├── controller/         # Controllers REST
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── TaskController.java
│   │   │   │   └── exception/
│   │   │   │       └── RestExceptionHandler.java
│   │   │   ├── service/            # Interfaces de servicios
│   │   │   │   └── impl/           # Implementaciones
│   │   │   ├── repository/         # JPA Repositories
│   │   │   ├── entity/             # Entidades JPA
│   │   │   ├── security/           # Configuración JWT
│   │   │   ├── exception/          # Excepciones personalizadas
│   │   │   ├── mapper/             # DTO Mappers
│   │   │   └── TaskManagerApplication.java
│   │   └── resources/
│   │       ├── openapi.yml         # Especificación OpenAPI
│   │       ├── application.yaml    # Configuración Spring
│   │       └── data.sql            # Datos iniciales H2
│   └── test/
└── pom.xml                         # Configuración Maven
```

---

## Arquitectura

### Patrón de Capas

```
┌─────────────────────────────────────────┐
│         Controllers (REST)              │ ← HTTP Requests
├─────────────────────────────────────────┤
│         Services (Business Logic)       │
├─────────────────────────────────────────┤
│         Repositories (Data Access)      │ ← H2 Database
└─────────────────────────────────────────┘
```

### Componentes Clave

**Controllers (`controller/`)**
- `AuthController`: Endpoint `/auth/login`
- `TaskController`: Endpoints `/tasks/**`

**Services (`service/`)**
- `AuthService`: Lógica de autenticación y generación de JWT
- `TaskService`: CRUD de tareas
- `JwtService`: Validación y extracción de claims JWT

**Repositories (`repository/`)**
- `UsuarioRepository`: Acceso a BD de usuarios
- `TareaRepository`: Acceso a BD de tareas
- `EstadoTareaRepository`: Acceso a estados

**Security (`security/`)**
- `SecurityConfig`: Configuración de Spring Security
- `JwtAuthenticationFilter`: Filtro que valida tokens JWT

**Exception Handling (`exception/`)**
- `RestExceptionHandler`: Centraliza manejo de excepciones
- `ApiError`: DTO para respuestas de error
- `ResourceNotFoundException`: Excepción para recursos no encontrados
- `UnauthorizedException`: Excepción para autenticación fallida

---

## Metodología API First

### Qué significa

La API fue diseñada **primero** usando OpenAPI 3.0, y el código fue generado a partir de la especificación.

### Flujo de Desarrollo

1. **Definir especificación** (`src/main/resources/openapi.yml`)
   - Endpoints, schemas, respuestas de error

2. **Generar código** (Maven build)
   ```bash
   mvn clean generate-sources
   ```
   - Plugin `openapi-generator-maven-plugin` genera interfaces en `target/generated-sources/openapi/`
   - Ubicación: `io.github.srdejo.task_manager.api.TasksApi`, `AuthApi`

3. **Implementar** (Controllers)
   - `TaskController` implementa interfaz generada `TasksApi`
   - `AuthController` implementa interfaz generada `AuthApi`

### Ventajas

- **Contrato claro**: La API está documentada antes de implementar
- **Consistencia** entre documentación y código
- **Swagger automático**: Generado directamente desde OpenAPI
- **Type-safety**: Interfaces tipadas generadas

### Archivo OpenAPI

Ubicación: `src/main/resources/openapi.yml`

```yaml
openapi: 3.0.1
info:
  title: Task API
  version: 1.0.0

paths:
  /auth/login:          # Endpoint de autenticación
  /tasks:               # Listar y crear tareas
  /tasks/{id}:          # Obtener, actualizar, eliminar por ID

components:
  schemas:              # Modelos de datos
  responses:            # Respuestas reutilizables
  securitySchemes:      # Definición de JWT
```

---

## Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|---|---|---|
| **Java** | 21 | Lenguaje de programación |
| **Spring Boot** | 3.5.14-SNAPSHOT | Framework web |
| **Spring Security** | - | Autenticación y autorización |
| **Spring Data JPA** | - | Acceso a datos |
| **JWT (JJWT)** | 0.13.0 | Tokens de autenticación |
| **H2 Database** | - | Base de datos en memoria |
| **Lombok** | - | Generación de boilerplate |
| **SpringDoc OpenAPI** | 2.8.16 | Documentación Swagger |
| **OpenAPI Generator** | 7.21.0 | Generación de código desde OpenAPI |
| **Maven** | 3.6+ | Gestor de dependencias |

---

## Notas Importantes

### Token JWT
- **Algoritmo**: HS512 (HMAC con SHA-512)
- **Duración**: 24 horas
- **Payload incluye**: username, issued-at, expiration

### Base de Datos H2
- **Tipo**: En memoria (se reinicia al reiniciar la app)
- **Datos iniciales**: Cargados automáticamente desde `data.sql`
- **DDL**: Generado automáticamente por Hibernate (`ddl-auto: create`)

### Seguridad
- Todas las rutas bajo `/tasks/**` requieren header `Authorization: Bearer <token>`
- Rutas públicas:
  - `POST /auth/login`
  - `GET /swagger-ui/**`
  - `GET /v3/api-docs/**`
  - `GET /openapi.yml`
  - `GET /h2-console/**`

### Validación
- Todos los campos de request son validados
- Respuestas incluyen detalles de validación en caso de error

---

## Solución de Problemas

### Error: Token inválido (401)
- Asegúrate de incluir el token completo en el header
- Verifica que el formato sea `Bearer <token>` (con espacio)
- El token caduca después de 24 horas

### Error: Usuario no encontrado
- Usa las credenciales correctas: `admin / admin123`
- Verifica que la BD se haya inicializado correctamente (revisar `/h2-console`)

### Error: Puerto 8080 en uso
```bash
# Cambiar puerto en application.yaml:
server:
  port: 8081
```

### Error de compilación
```bash
# Limpiar y recompilar
mvn clean compile
```

---
## Licencia

Este proyecto fue desarrollado como parte de un desafío técnico.
