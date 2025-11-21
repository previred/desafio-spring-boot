# ADR-001: Arquitectura API REST para Gestión de Tareas

**Status:** DRAFT
**Fecha:** 2025-11-21

## Contexto

NUEVO SPA necesita una plataforma para mejorar la productividad de sus equipos. El problema es sencillo: los equipos pierden tiempo coordinando tareas manualmente. La solución es una API que permita crear, asignar y dar seguimiento a tareas de forma centralizada, con autenticación para que cada usuario vea solo lo suyo.

## Decisiones Técnicas

### API First con OpenAPI Generator

Decidimos definir el contrato antes de escribir código. El archivo `openapi.yml` es la fuente de verdad; el plugin de Maven genera las interfaces de controllers y DTOs. Esto evita que la documentación y el código se desincronicen (algo que he visto romper integraciones en producción más de una vez).

### Arquitectura en Capas

Mantenemos la estructura clásica de Spring: Controller -> Service -> Repository -> Entity. Los controllers generados delegan toda la lógica a servicios, manteniendo las capas limpias y testeables de forma independiente.

### Seguridad

Usamos Spring Security 6 con `SecurityFilterChain` (el `WebSecurityConfigurerAdapter` está deprecado desde 5.7). Un filtro JWT intercepta las requests y valida tokens. Los endpoints públicos son solo `/api/v1/auth/**` y la documentación Swagger.

### Modelo de Datos

Tres tablas con relaciones simples:
- **Usuario**: id, username, password (BCrypt), email, fullName
- **Tarea**: id, titulo, descripcion, fechas, usuario_id (FK), estado_id (FK)
- **EstadoTarea**: id, nombre (TODO, IN_PROGRESS, DONE)

Los datos iniciales (usuarios y estados) se cargan via `data.sql` para que la aplicación funcione desde el primer arranque.

## Stack

| Componente | Tecnología | Nota |
|------------|------------|------|
| Runtime | Java 21 | Records para DTOs inmutables |
| Framework | Spring Boot 3.5.x | - |
| Build | Maven | - |
| BD | H2 (file mode) | Persiste entre reinicios |
| Seguridad | Spring Security 6 + jjwt | - |
| Docs | springdoc-openapi | - |
| API First | openapi-generator-maven-plugin | - |
| Testing | JUnit 5 + Mockito | TDD para auth y permisos |

## Endpoints

```
POST /api/v1/auth/register    → Registro de usuario
POST /api/v1/auth/login       → Autenticación, devuelve JWT

GET  /api/v1/tasks            → Listar tareas (paginado, filtro por estado)
POST /api/v1/tasks            → Crear tarea
GET  /api/v1/tasks/{id}       → Obtener tarea
PUT  /api/v1/tasks/{id}       → Actualizar tarea
DELETE /api/v1/tasks/{id}     → Eliminar tarea

GET  /api/v1/task-states      → Listar estados disponibles
```

## Consideraciones

El manejo de errores se centraliza en un `@ControllerAdvice` que devuelve respuestas consistentes con timestamp, status y detalles de validación. Para validación de entrada usamos Bean Validation en los DTOs generados.

La consola H2 queda habilitada en desarrollo (`/h2-console`) para debugging rápido, pero debe deshabilitarse en producción.

---
*Este ADR será actualizado a ACCEPTED una vez validada la implementación.*
