# Task Manager API -- Desafío Técnico PreviRed

## Descripción

API RESTful desarrollada con Spring Boot 3.5.x y Java 21 para la gestión
de tareas con autenticación JWT.

La aplicación permite:

-   Autenticación de usuarios mediante JWT
-   Crear, listar, actualizar y eliminar tareas
-   Persistencia en base de datos H2 en memoria
-   Documentación OpenAPI 3 con Swagger UI

------------------------------------------------------------------------

## Tecnologías utilizadas

-   Java 21
-   Spring Boot 3.5.x
-   Spring Security (JWT)
-   Spring Data JPA
-   H2 Database (in-memory)
-   Maven
-   OpenAPI 3 (springdoc)
-   Swagger UI

------------------------------------------------------------------------

## Justificación de Arquitectura (Clean Architecture)

Se implementó Clean Architecture con los siguientes objetivos:

1.  Independencia del framework\
    El dominio no depende de Spring, JPA ni infraestructura. Esto
    permite reemplazar tecnologías sin afectar las reglas de negocio.

2.  Separación clara de responsabilidades

    -   Domain: Modelos y contratos del negocio\
    -   Application: Casos de uso\
    -   Infrastructure: Controllers, JPA, JWT, OpenAPI

3.  Testabilidad\
    Los casos de uso pueden probarse sin levantar el contexto de Spring.

4.  Escalabilidad futura\
    Facilita migración a microservicios o arquitectura orientada a
    eventos.

5.  Aplicación de principios SOLID\
    Especialmente Dependency Inversion y Single Responsibility.

------------------------------------------------------------------------

## Cómo ejecutar el proyecto

Compilar:

mvn clean install

Ejecutar:

mvn spring-boot:run

Aplicación disponible en:

http://localhost:8080

------------------------------------------------------------------------

## Usuarios precargados

| Username | Password |
|----------|----------|
| admin    | 1234     |
| user     | 1234     |

------------------------------------------------------------------------

## Estados tareas precargados

| Id | Name |
|----|------|
| 1  | PENDING |
| 2  | IN_PROGRESS |
| 3  | COMPLETED |

------------------------------------------------------------------------

## Autenticación JWT

### Obtener token

curl -X POST http://localhost:8080/auth/login\
-H "Content-Type: application/json"\
-d '{"username":"admin","password":"1234"}'

Respuesta:

{ "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }

### Usar el token:

curl -X GET http://localhost:8080/tasks \
-H "Authorization: Bearer `<token>`"

------------------------------------------------------------------------

## CRUD de Tareas

### Crear tarea

curl -X POST http://localhost:8080/tasks\
-H "Authorization: Bearer `<token>`"\
-H "Content-Type: application/json"\
-d '{ "title":"Nueva tarea", "description":"Descripción", "userId":1,
"status":"PENDING" }'

### Listar tareas

curl -X GET http://localhost:8080/tasks\
-H "Authorization: Bearer `<token>`"

### Obtener tarea por ID

curl -X GET http://localhost:8080/tasks/1\
-H "Authorization: Bearer `<token>`"

### Actualizar tarea

curl -X PUT http://localhost:8080/tasks/1\
-H "Authorization: Bearer `<token>`"\
-H "Content-Type: application/json"\
-d '{ "status":"IN_PROGRESS" }'

### Eliminar tarea

curl -X DELETE http://localhost:8080/tasks/1\
-H "Authorization: Bearer `<token>`"

------------------------------------------------------------------------

## Pruebas negativas recomendadas

###   Acceder sin token → 401

curl -X GET http://localhost:8080/tasks

###  Crear tarea con estado inválido → 400

curl -X POST http://localhost:8080/tasks \
-H "Authorization: Bearer <token>" \
-H "Content-Type: application/json" \
-d '{
"title": "Tarea inválida",
"description": "Estado incorrecto",
"userId": 1,
"status": "DONE"
}'

###   Obtener tarea inexistente → 404

curl -X POST http://localhost:8080/tasks \
-H "Authorization: Bearer <token>" \
-H "Content-Type: application/json" \
-d '{
"title": "Tarea sin usuario",
"description": "Usuario no existe",
"userId": 999,
"status": "PENDING"
}'

------------------------------------------------------------------------

## Colección Postman

La colección se encuentra en la carpeta `/postman`.

Variables requeridas:
- baseUrl → http://localhost:8080
- token → JWT obtenido desde /auth/login

------------------------------------------------------------------------

## Documentación OpenAPI

Swagger UI:

http://localhost:8080/swagger-ui/index.html

OpenAPI JSON:

http://localhost:8080/v3/api-docs

OpenAPI YAML:

http://localhost:8080/v3/api-docs.yaml

Contrato OpenAPI incluido en el repositorio:

openapi.yml

------------------------------------------------------------------------

## Perfil de ejecución

La aplicación utiliza perfiles:

-   dev → H2 console habilitada
-   prod → H2 console deshabilitada

------------------------------------------------------------------------

## Mejoras futuras

-   Tests unitarios de casos de uso
-   Tests de integración con MockMvc
-   Manejo de excepciones personalizadas por tipo
-   Persistencia en base de datos externa (PostgreSQL)
-   Despliegue en AWS (Docker + ECS / EKS)

------------------------------------------------------------------------

Desarrollado como solución al desafío técnico para el cargo de
Desarrollador Senior Backend -- PreviRed.
