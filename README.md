# Sistema de Gestión de Tareas - Previred

## 👤 Postulante

| Campo   | Detalle                      |
|---------|------------------------------|
| Nombre  | Luis Martinez                |
| Correo  | l.martinezm22@gmail.com      |
| Cargo   | Desarrollador Backend Java   |

---

API RESTful para gestión de tareas desarrollada con **Spring Boot 3.5.0**, **Java 21** y **metodología API First** usando OpenAPI.

**Desafío Técnico para Previred**

## 🎯 Características Principales

- ✅ **API First**: Desarrollo basado en especificación OpenAPI 3.0
- ✅ **Java 21**: Uso de features modernas (Records, Streams, Lambdas)
- ✅ **Spring Boot 3.5.0**: Última versión del framework
- ✅ **JWT Authentication**: Autenticación segura con tokens
- ✅ **H2 Database**: Base de datos en memoria con datos precargados
- ✅ **JPA/Hibernate**: Capa de persistencia
- ✅ **OpenAPI/Swagger**: Documentación interactiva de la API
- ✅ **SOLID Principles**: Código limpio y mantenible
- ✅ **Arquitectura Hexagonal**: Separación de responsabilidades

## 📋 Requisitos Previos

- Java 21 o superior
- Maven 3.9.x o superior

## 🚀 Ejecución de la Aplicación

### 1. Compilar el proyecto

```bash
mvn clean package
```

### 2. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

O ejecutar el JAR generado:

```bash
java -jar target/desafio-tareas-1.0.0.jar
```

La aplicación se iniciará en `http://localhost:8080`

## 📚 Documentación de la API

Una vez iniciada la aplicación, accede a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:taskdb`
  - Usuario: `sa`
  - Password: (vacío)

## 🗄️ Estructura de Base de Datos

### Tabla: usuarios (Precargada)

| ID  | Username | Email                   | Password   |
|-----|----------|-------------------------|------------|
| 1   | admin    | admin@previred.com      | admin123   |
| 2   | user     | user@previred.com       | user123    |
| 3   | manager  | manager@previred.com    | manager123 |

### Tabla: estados_tarea (Precargada)

| ID  | Nombre       |
|-----|--------------|
| 1   | PENDIENTE    |
| 2   | EN_PROGRESO  |
| 3   | COMPLETADA   |
| 4   | CANCELADA    |

### Tabla: tareas

| Campo          | Tipo         | Descripción                    |
|----------------|--------------|--------------------------------|
| id             | BIGINT       | ID único de la tareaEntity           |
| titulo         | VARCHAR(100) | Título de la tareaEntity             |
| descripcion    | VARCHAR(500) | Descripción detallada          |
| fecha_creacion | TIMESTAMP    | Fecha de creación              |
| prioridad      | VARCHAR(20)  | BAJA, MEDIA, ALTA              |
| estado_id      | BIGINT       | FK a estados_tarea             |
| usuario_id     | BIGINT       | FK a usuarios                  |

## 🔐 Autenticación

### 1. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwOTU5...",
  "username": "admin",
  "email": "admin@previred.com"
}
```

**⚠️ Importante**: Guarda el token JWT devuelto. Lo necesitarás para todas las demás operaciones.

## 📝 Operaciones CRUD de Tareas

Todas las operaciones requieren el header de autenticación:
```
Authorization: Bearer {token}
```

### 2. Crear Tarea

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {tu-token-jwt}" \
  -d '{
    "titulo": "Implementar módulo de reportes",
    "descripcion": "Crear módulo completo de generación de reportes mensuales en formato PDF",
    "prioridad": "ALTA",
    "estadoId": 1,
    "usuarioId": 1
  }'
```

**Respuesta (201 Created):**
```json
{
  "id": 1,
  "titulo": "Implementar módulo de reportes",
  "descripcion": "Crear módulo completo de generación de reportes mensuales en formato PDF",
  "fechaCreacion": "2026-03-04T22:00:00Z",
  "prioridad": "ALTA",
  "estado": {
    "id": 1,
    "nombre": "PENDIENTE"
  },
  "usuarioEntity": {
    "id": 1,
    "username": "admin",
    "email": "admin@nuevospa.com"
  }
}
```

### 3. Listar Todas las Tareas

```bash
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer {tu-token-jwt}"
```

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Implementar módulo de reportes",
    "descripcion": "Crear módulo completo de generación de reportes mensuales en formato PDF",
    "fechaCreacion": "2026-03-04T22:00:00Z",
    "prioridad": "ALTA",
    "estado": {
      "id": 1,
      "nombre": "PENDIENTE"
    },
    "usuarioEntity": {
      "id": 1,
      "username": "admin",
      "email": "admin@previred.com"
    }
  }
]
```

### 4. Obtener Tarea por ID

```bash
curl -X GET http://localhost:8080/api/tasks/1 \
  -H "Authorization: Bearer {tu-token-jwt}"
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "titulo": "Implementar módulo de reportes",
  "descripcion": "Crear módulo completo de generación de reportes mensuales en formato PDF",
  "fechaCreacion": "2026-03-04T22:00:00Z",
  "prioridad": "ALTA",
  "estado": {
    "id": 1,
    "nombre": "PENDIENTE"
  },
  "usuarioEntity": {
    "id": 1,
    "username": "admin",
    "email": "admin@nuevospa.com"
  }
}
```

### 5. Actualizar Tarea

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {tu-token-jwt}" \
  -d '{
    "titulo": "Implementar módulo de reportes actualizado",
    "descripcion": "Crear módulo completo de generación de reportes mensuales en formato PDF y Excel",
    "prioridad": "MEDIA",
    "estadoId": 2,
    "usuarioId": 2
  }'
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "titulo": "Implementar módulo de reportes actualizado",
  "descripcion": "Crear módulo completo de generación de reportes mensuales en formato PDF y Excel",
  "fechaCreacion": "2026-03-04T22:00:00Z",
  "prioridad": "MEDIA",
  "estado": {
    "id": 2,
    "nombre": "EN_PROGRESO"
  },
  "usuarioEntity": {
    "id": 2,
    "username": "user",
    "email": "user@nuevospa.com"
  }
}
```

### 6. Eliminar Tarea

```bash
curl -X DELETE http://localhost:8080/api/tasks/1 \
  -H "Authorization: Bearer {tu-token-jwt}"
```

**Respuesta (204 No Content):**
Sin contenido en el body, solo status 204.

## 🎨 Arquitectura del Proyecto

```
src/main/java/com/previred/desafiostareas/
├── DesafioTareasApplication.java  # Clase principal
├── application/
│   └── service/                   # Lógica de negocio (Services)
├── domain/
│   ├── entity/                    # Entidades JPA
│   └── repository/                # Repositorios JPA
├── infrastructure/
│   ├── controller/                # Controladores REST
│   ├── mapper/                    # Mappers DTO <-> Entity
│   ├── security/                  # Configuración de seguridad y JWT
│   └── exception/                 # Manejadores de excepciones
└── api/                           # Generado por OpenAPI Generator
    ├── model/                     # DTOs generados
    └── *.java                     # Interfaces de API generadas
```

## 🛠️ Tecnologías Utilizadas

| Tecnología             | Versión | Propósito                              |
|------------------------|---------|----------------------------------------|
| Java                   | 21      | Lenguaje de programación               |
| Spring Boot            | 3.5.0   | Framework principal                    |
| Spring Security        | 6.x     | Autenticación y autorización           |
| Spring Data JPA        | 3.x     | Persistencia de datos                  |
| H2 Database            | 2.x     | Base de datos en memoria               |
| JJWT                   | 0.12.5  | Generación y validación de JWT         |
| Lombok                 | 1.18.x  | Reducción de boilerplate               |
| OpenAPI Generator      | 7.3.0   | Generación de código desde OpenAPI     |
| Springdoc OpenAPI      | 2.3.0   | Documentación Swagger                  |
| Maven                  | 3.9.x   | Gestor de dependencias                 |

## 🎯 Principios SOLID Aplicados

- **Single Responsibility**: Cada clase tiene una única responsabilidad
- **Open/Closed**: Abierto a extensión, cerrado a modificación
- **Liskov Substitution**: Las interfaces generadas son sustituibles
- **Interface Segregation**: Interfaces específicas por funcionalidad
- **Dependency Inversion**: Dependencias mediante abstracciones

## 🔒 Seguridad

- Contraseñas hasheadas con BCrypt
- Autenticación stateless con JWT
- Tokens con expiración de 24 horas
- Endpoints protegidos por Spring Security
- Validación de datos en DTOs

## 📊 Metodología API First

Este proyecto fue desarrollado siguiendo la metodología **API First**:

1. **Diseño**: Creación del contrato OpenAPI (`openapi.yml`)
2. **Generación**: Uso de `openapi-generator-maven-plugin` para generar:
   - Interfaces de controladores
   - DTOs (modelos)
   - Validaciones
3. **Implementación**: Los controladores implementan las interfaces generadas
4. **Documentación**: Swagger UI generado automáticamente

### Comando de generación:
```bash
mvn clean compile
```

Esto ejecuta el plugin que genera el código en `target/generated-sources/openapi/`

## 📝 Notas Técnicas

- La base de datos H2 se recrea en cada inicio de la aplicación
- Los datos de usuarios y estados están precargados en `data.sql`
- El esquema de la base de datos se define en `schema.sql`
- La secret key de JWT está en `application.yml` (en producción usar variables de entorno)

## 👨‍💻 Autor

**Luis Martinez**
- Email: luis.martinez@example.com
- Cargo: Backend Developer Senior - Java Spring Boot

---
## 📄 Licencia

Este proyecto fue desarrollado como parte de una prueba técnica para Previred.
Este proyecto fue desarrollado como parte de una prueba técnica para NUEVO SPA.
