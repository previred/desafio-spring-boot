# Desafío Spring Boot
Aplicación de gestión de tareas con usuarios, roles y estados, desarrollada en **Spring Boot 3 + Java 21**.
Incluye seguridad con JWT, documentación de endpoints con Springdoc OpenAPI, y pruebas unitarias con JUnit/Mockito.

## Levantar la aplicación
### Requisitos previos
- Java 21
- Maven 3.9+
- H2 Database (embebida, no requiere instalación)
- Spring Boot 3.x

### Pasos
1. Clonar el repositorio:
```
git clone https://github.com/tuusuario/desafiospringboot.git
cd desafiospringboot
```
2. Compilar y ejecutar:
```
mvn clean install
mvn spring-boot:run
```
3. La aplicación quedará disponible en:
```
http://localhost:8080
```
---
## Autenticación
- El sistema utiliza JWT.
- Primero debes autenticarte con el endpoint de login para obtener un token:
```
POST /api/auth/login
{
"username": "admin",
"password": "admin123"
}
```
- El token recibido se debe enviar en el header:
```
Authorization: Bearer <token>
```
---
## Documentación de API
Una vez levantada la aplicación, puedes acceder a la documentación interactiva en Swagger/OpenAPI:

```
http://localhost:8080/swagger-ui/index.html
```
---
## Endpoints principales
### Usuarios
- `POST /api/users` → Crear usuario
- `GET /api/users/{id}` → Obtener usuario por ID
- `GET /api/users` → Listar usuarios

### Tareas
- `POST /api/tasks` → Crear tarea
- `GET /api/tasks/{id}` → Obtener tarea por ID
- `GET /api/tasks` → Listar todas las tareas
- `GET /api/tasks/user/{id}` → Listar tareas de un usuario
- `PUT /api/tasks/{id}` → Actualizar tarea
- `PUT /api/tasks/{id}/assign/{userId}` → Asignar tarea a usuario
- `DELETE /api/tasks/{id}` → Eliminar tarea

### Autenticación
- `POST /api/auth/login` → Obtener token JWT


--------------------------------------------------------------
# Desafío Técnico: Gestión de Tareas con Spring Boot y Java

La empresa NUEVO SPA desea desarrollar una plataforma de gestión de tareas para mejorar la productividad de sus equipos. El sistema debe permitir a los usuarios crear, actualizar, eliminar y listar tareas. Además, se requiere autenticación mediante JWT y documentación de la API utilizando OpenAPI.

## Objetivo:
Crear una API RESTful utilizando Spring Boot que gestione usuarios y tareas, aplicando buenas prácticas, principios SOLID y utilizando las tecnologías especificadas.

## Requisitos Técnicos:
### Java:
- Utiliza Java 21 para la implementación.
- Utiliza las características de Java 21, como lambdas, records y streams, cuando sea apropiado.
- Utilizar Maven como gestor de dependencias.

### Spring Boot 3.5.x:
- Construye la aplicación utilizando Spring Boot 3.5.x (última versión disponible).

### Base de Datos:

- Utiliza una base de datos H2.
- Crea tres tablas: usuarios, tareas y estados_tarea.
- La tabla usuarios debe contener datos pre cargados.
- La tabla estados_tarea debe contener estados pre cargados.

### JPA:
- Implementa una capa de persistencia utilizando JPA para almacenar y recuperar las tareas.

### JWT (JSON Web Token):

- Implementa la autenticación utilizando JWT para validar usuarios.

### OpenAPI y Swagger:

- Documenta la API utilizando OpenAPI.

## Funcionalidades:
### Autenticación:
- Implementa un endpoint para la autenticación de usuarios utilizando JWT. 

### CRUD de Tareas:
- Implementa operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para las tareas.

## Consideraciones:
### Seguridad:
- Asegúrate de que las operaciones CRUD de tareas solo sean accesibles para usuarios autenticados.

### Documentación:
- Utiliza OpenAPI 3 para documentar claramente la API.
- Puntos adicionales si se genera el API mediante metodologia API First. Generar el archivo openapi.yml Nota: Ejemplo Plugin Maven groupId org.openapitools, artifactId openapi-generator-maven-plugin

### Buenas prácticas:
- Escribe código ordenado, aplicando buenas prácticas y principios SOLID.

### Creatividad
- Se espera dada la descripción del problema se creen las entidades y metodos en consecuencia a lo solicitado.

## Entregables:
### Repositorio de GitHub:
- Realiza un Pull request a este repositorio indicando tu nombre, correo y cargo al que postulas.
- Todos los PR serán rechazados, no es un indicador de la prueba.

### Documentación:
- Incluye instrucciones claras en un README en formato markdown, sobre cómo ejecutar y probar la aplicación, con ejemplos de requests.

## Evaluación:
Se evaluará la solución en función de los siguientes criterios:

- Correcta implementación de las funcionalidades solicitadas.
- Aplicación de buenas prácticas de desarrollo, patrones de diseño y principios SOLID.
- Uso adecuado de Java 21, Spring Boot 3.5.x, H2, JWT, OpenAPI.
- Claridad y completitud de la documentación.
- **Puntos extras si la generación de la API se realizo mediante API First**
