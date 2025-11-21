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

---

# Guía de Ejecución

## Requisitos Previos
- Java 21
- Maven 3.6+

## Levantar el Proyecto

```bash
# 1. Clonar el repositorio
git clone <url-repo>
cd desafio-spring-boot

# 2. Compilar y ejecutar tests
./mvnw clean test

# 3. Ejecutar la aplicación
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Endpoints Principales

### Autenticación
- `POST /api/v1/auth/register` - Registrar usuario
- `POST /api/v1/auth/login` - Login y obtener JWT

### Tareas (requiere autenticación)
- `GET /api/v1/tasks` - Listar tareas
- `POST /api/v1/tasks` - Crear tarea
- `GET /api/v1/tasks/{id}` - Obtener tarea
- `PUT /api/v1/tasks/{id}` - Actualizar tarea
- `DELETE /api/v1/tasks/{id}` - Eliminar tarea

### Estados
- `GET /api/v1/task-states` - Listar estados disponibles

## Documentación API

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Tecnologías Implementadas

- ✅ **Java 21** (Records, Pattern Matching, etc.)
- ✅ **Spring Boot 3.4.1** con SecurityFilterChain moderno
- ✅ **JWT** con jjwt 0.12.6
- ✅ **H2 Database** con datos pre-cargados
- ✅ **MapStruct 1.6.3** para DTOs
- ✅ **OpenAPI Generator** (Contract-First)
- ✅ **23 Tests** con JUnit 5 y Mockito
- ✅ **Bruno Collection** para testing de API

## Arquitectura

```
3 Capas: Controller → Service → Repository
DTOs con MapStruct
Security con JWT Filter
OpenAPI Spec → Código Generado
```
