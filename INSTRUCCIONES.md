# Sistema de Gestión de Tareas - Spring Boot - Daniel Segovia

## Descripción

API RESTful implementada con **Spring Boot** para la gestión de tareas, permitiendo a los usuarios crear, actualizar, eliminar y filtrar tareas. El sistema incluye autenticación mediante JWT, gestión de estados de tareas, y está completamente documentado con OpenAPI/Swagger.

## Requisitos Cumplidos

* ✅ Spring Boot 3.4.5
* ✅ Java 17
* ✅ Base de datos H2 en memoria
* ✅ Spring Data JPA (Hibernate)
* ✅ Maven para gestión de dependencias
* ✅ Autenticación JWT
* ✅ Manejo de excepciones centralizado
* ✅ Documentación OpenAPI/Swagger
* ✅ Control de acceso basado en roles

## Tecnologías Utilizadas

* Spring Boot 3.4.5
* Spring Security
* Spring Data JPA
* H2 Database
* JWT (JSON Web Tokens)
* Lombok
* MapStruct
* Swagger/OpenAPI
* Maven

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── dsegovia/desafio/
│   │       ├── config/          # Configuraciones (Spring Security, etc.)
│   │       ├── controller/      # Controladores REST
│   │       ├── dto/             # Data Transfer Objects
│   │       │   └── error/       # DTOs para respuestas de error
│   │       ├── entity/          # Entidades JPA
│   │       ├── event/           # Eventos del sistema
│   │       ├── exception/       # Excepciones personalizadas
│   │       ├── mapper/          # Mappers (MapStruct)
│   │       ├── repository/      # Repositorios JPA
│   │       ├── security/        # Configuración de seguridad y JWT
│   │       └── service/         # Servicios de negocio
│   └── resources/
│       ├── application.properties
│       ├── data.sql             # Datos iniciales
│       └── openapi/             # Especificación OpenAPI
```

## Cómo Ejecutar

### Prerrequisitos

* **JDK 17** o superior
* **Maven 3.6+**

### Pasos

**Clonar el repositorio:**

**Compilar el proyecto:**
```
mvn clean install
```

**Ejecutar la aplicación:**
```
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:8080

## Despliegue con Docker

El proyecto incluye un Dockerfile optimizado para facilitar el despliegue en contenedores Docker.

### Prerrequisitos

* Docker instalado en su sistema
* Acceso a la terminal/línea de comandos

### Pasos para despliegue con Docker

**Construir la imagen Docker:**
```
docker build -t gestion-tareas-app .
```

**Ejecutar el contenedor:**
```
docker run -p 8080:8080 gestion-tareas-app
```

## Consola H2

* **URL**: http://localhost:8080/h2-console
* **JDBC URL**: `jdbc:h2:mem:dsegovia`
* **Usuario**: `dsegovia`
* **Contraseña**: `dsegovia`

## Documentación API (Swagger)

* **Swagger UI**: http://localhost:8080/swagger-ui.html
* **OpenAPI JSON**: http://localhost:8080/api-docs
---
## Pruebas con Postman

Se incluye colección Postman en el repositorio:
- Archivo: `ColeccionPostmanDSEGOVIA.JSON`

---


## Endpoints Disponibles

### Autenticación

* `POST /auth/login` - Iniciar sesión y obtener token JWT

### Tareas

* `GET /api/tareas` - Listar tareas (admite filtrado por estado, prioridad y etiqueta)
* `GET /api/tareas/{id}` - Obtener tarea por ID
* `POST /api/tareas` - Crear nueva tarea
* `PUT /api/tareas/{id}` - Actualizar tarea completa
* `DELETE /api/tareas/{id}` - Eliminar tarea

### Estados de Tarea

* `GET /api/estados` - Listar todos los posibles estados de tarea

## Modelo de Datos

### Tarea

* `id`: Long - Identificador único
* `titulo`: String - Título de la tarea
* `descripcion`: String - Descripción detallada
* `fechaCreacion`: LocalDateTime - Fecha de creación
* `fechaVencimiento`: LocalDateTime - Fecha límite
* `prioridad`: Enum (ALTA, MEDIA, BAJA) - Nivel de prioridad
* `estado`: EstadoTarea - Estado actual
* `owner`: Usuario - Propietario de la tarea
* `etiquetas`: String - Lista de etiquetas separadas por comas

### EstadoTarea

* `id`: Long - Identificador único
* `nombre`: String - Nombre del estado (PENDIENTE, EN_PROGRESO, BLOQUEADA, etc.)

### Usuario

* `id`: Long - Identificador único
* `username`: String - Nombre de usuario
* `password`: String - Contraseña (encriptada)
* `enabled`: boolean - Estado de activación
* `roles`: Set<Rol> - Roles asignados

## Características de Seguridad

* **Autenticación JWT**: Tokens con tiempo de expiración configurable
* **Contraseñas encriptadas**: Utiliza BCrypt para almacenamiento seguro
* **Roles y permisos**: Implementa ROLE_USER y ROLE_ADMIN
* **Manejo centralizado de excepciones**: Respuestas de error consistentes
* **Validación de propiedad**: Los usuarios solo pueden modificar sus propias tareas (a menos que sean administradores)

## Formato de Respuestas

### Respuesta de Login Exitoso:

```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "userId": 1,
    "username": "admin",
    "roles": ["ROLE_USER", "ROLE_ADMIN"]
}
```

### Respuesta de Tarea:

```json
{
    "id": 1,
    "titulo": "Implementar autenticación JWT",
    "descripcion": "Crear filtros y configuración para autenticación basada en JWT",
    "fechaCreacion": "2025-05-06T10:30:00",
    "fechaVencimiento": "2025-05-13T10:30:00",
    "prioridad": "ALTA",
    "ownerId": 2,
    "estadoId": 2,
    "etiquetas": ["backend", "seguridad", "jwt"],
    "estadoNombre": "EN_PROGRESO",
    "ownerUsername": "admin"
}
```

### Respuesta de Error:

```json
{
    "timestamp": "2025-05-06T15:30:45",
    "status": 401,
    "error": "Unauthorized",
    "message": "El token ha expirado. Por favor, inicie sesión nuevamente",
    "path": "/api/tareas",
    "requestId": "7a5d8f3b-c2e1-4d9a-b8c7-e6f5d4c3b2a1"
}
```

## Usuarios Predefinidos

El sistema incluye dos usuarios predefinidos para pruebas:

1. **Usuario regular**:
    - Username: `user`
    - Password: `password123`
    - Roles: `ROLE_USER`

2. **Usuario administrador**:
    - Username: `admin`
    - Password: `admin123`
    - Roles: `ROLE_USER`, `ROLE_ADMIN`

## Estados de Tarea Predefinidos

Los estados de tarea predefinidos son:
1. PENDIENTE
2. EN_PROGRESO
3. BLOQUEADA
4. EN_REVISION
5. COMPLETADA
6. CANCELADA

## Características Destacadas

* **Filtrado de tareas**: Por estado, prioridad y etiquetas
* **Control de acceso**: Usuarios normales solo ven sus tareas, administradores ven todas
* **Manejo robusto de errores**: Respuestas JSON detalladas para diferentes tipos de error
* **Documentación completa**: Especificación OpenAPI generada
* **Arquitectura en capas**: Separación clara de responsabilidades (controladores, servicios, repositorios)

## Notas Adicionales

* La duración del token JWT es configurable en application.properties
* Las tareas soportan múltiples etiquetas para facilitar la organización
* Se utiliza H2 en memoria para facilitar las pruebas, en un entorno de producción se recomendaría una base de datos persistente
* Se agregan test unitarios para clases IMPL