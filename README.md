# 🧘‍♂️ Nuevo SPA - API de Administración de Tareas

API RESTful para gestionar tareas, autenticación de usuarios y asignacion de tareas dentro de un SPA.

------------------------------------------------------------------------

## 🧔🏻‍♂️ Autor
- **Nombre:** Juan Ángel Ochoa Gómez
- **Correo:** juan8a2411@gmail.com
- **Cargo:** Desarrollador Java
------------------------------------------------------------------------

## 📌 Descripción

Esta API permite:

-   Autenticar usuarios mediante **JWT**
-   Crear, listar, actualizar y eliminar **tareas**



------------------------------------------------------------------------
## 💡 Ejecución y Pruebas
```
1.clonar proyecto
2.mvn clean install
3.mvn spring-boot:run
```
------------------------------------------------------------------------
### Accesos
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Console H2: `http://localhost:8080/h2-console`

### Usuarios Precargados para las pruebas
- **username: admin password: admin**
- **username: employee password: employee**

### [Ir a Curl para pruebas](#ejemplos-curl)

## 🚀 Tecnologías

-   **Java 21**
-   **Spring Boot 3.5.8**
-   **metodologia API First [`openapi.yml`](src/main/resources/openapi.yml)**
-   **OpenAPI 3.0.4**
-   **JWT Authentication**
-   **Base de Datos H2**
-   **RESTful API Design**

------------------------------------------------------------------------

## 🏗️ Arquitectura del Proyecto

``` plaintext
┌─────────────────────────┐
│      Controller         │  → Maneja las solicitudes HTTP
└───────────┬─────────────┘
            │
┌───────────▼─────────────┐
│        Service           │  → Lógica de negocio
└───────────┬─────────────┘
            │
┌───────────▼─────────────┐
│       Repository         │  → Acceso a datos (JPA)
└───────────┬─────────────┘
            │
┌───────────▼─────────────┐
│        Database          │
└──────────────────────────┘
```

------------------------------------------------------------------------

# 🔒 Flujo de Autenticación JWT

``` plaintext
[Cliente]
    |
    | POST /auth/login (username + password)
    v
[API - AuthController]
    |
    | Recibe Petición
    v
[AuthService]
    |
    | Valida Credenciales,Genera JWT
    v
[API responde token]
    |
    | Cliente envía: Authorization: Bearer <token>
    v
[Filtro JWT valida token en cada request]
```

------------------------------------------------------------------------

# 📚 Endpoints

| Módulo | Método | Endpoint        | Descripción            | Auth |
|--------|--------|------------------|-------------------------|------|
| Auth   | POST   | `/auth/login`    | Autentica usuario       | ❌   |
| Tasks  | GET    | `/tasks`         | Lista tareas            | ✅   |
| Tasks  | POST   | `/tasks`         | Crea tarea              | ✅   |
| Tasks  | GET    | `/tasks/{id}`    | Obtener tarea           | ✅   |
| Tasks  | PUT    | `/tasks/{id}`    | Actualizar tarea        | ✅   |
| Tasks  | PATCH  | `/tasks/{id}`    | Actualización parcial   | ✅   |
| Tasks  | DELETE | `/tasks/{id}`    | Eliminar tarea          | ✅   |


------------------------------------------------------------------------
# Ejemplos cURL

### Login:
``` bash
curl -X POST http://localhost:8080/api/auth/login   -H "Content-Type: application/json"   -d '{"username": "user", "password": "user123456"}'
```

### Obtener todas las tareas:
``` bash
curl -X GET "http://localhost:8080/api/tasks" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Crear tarea:
``` bash
curl -X POST http://localhost:8080/api/tasks   -H "Authorization: Bearer YOUR_JWT_TOKEN"   -H "Content-Type: application/json"   -d '{"title": "Masaje", "description": "Completo", "statusId":1, "assignedTo":2}'
```

### Obtener tarea por id:
``` bash
curl -X GET "http://localhost:8080/api/tasks/10" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Actualizar tarea completa:
``` bash
curl -X PUT "http://localhost:8080/api/tasks/10" -H "Authorization: Bearer YOUR_JWT_TOKEN" -H "Content-Type: application/json" -d '{"title": "Masaje Renovado","description": "Nueva descripción","statusId": 2,"assignedTo": 3}'
```

### Actualizar tarea parcial:
``` bash
curl -X PATCH "http://localhost:8080/api/tasks/10" -H "Authorization: Bearer YOUR_JWT_TOKEN" -H "Content-Type: application/json" -d '{"statusId": 3}'
```

### Eliminar tarea:
``` bash
curl -X DELETE "http://localhost:8080/api/tasks/10" -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

------------------------------------------------------------------------