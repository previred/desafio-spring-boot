# 🛠️ Gestión de Tareas con Spring Boot

Este proyecto es una API REST que permite gestionar tareas. Implementa autenticación con JWT, documentación con OpenAPI y Swagger, y sigue principios SOLID y mejores prácticas de desarrollo.

---

## 📄 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.4.0**
- **Spring Security (JWT)**
- **Spring Data JPA (Hibernate)**
- **H2 Database (Base de datos en memoria)**
- **Lombok**
- **Swagger con OpenAPI 3**
- **Maven como gestor de dependencias**
- **API First (OpenAPI Generator)**

---

## 🛠️ Instalación y Configuración

### 🔹 1. Clonar el repositorio

```bash
git clone https://github.com/previred/desafio-spring-boot.git
cd gestion-tareas
```

### 🔹 2. Configurar las variables de entorno

Crea un archivo `` en `src/main/resources/` con la siguiente configuración:

```properties
# Configuración 
spring.application.name=desafio-spring-boot
spring.jackson.deserialization.fail-on-unknown-properties=true

# Configuración de la base de datos H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:desafio
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Clave secreta para JWT
jwt.secret=eXNyYTI1NlNlY3JldFNlY3JldEhhc2g2NDMySW5QbGFjZQ==
```

### 🔹 3. Construir el proyecto

```bash
mvn clean install
```

### 🔹 4. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La API estará disponible en:📍 [**http://localhost:8080**](http://localhost:8080)


---

## 🔐 Autenticación con JWT

El sistema usa JWT para proteger los endpoints. Para obtener un token, inicia sesión con:

### 👉 **Login**

**Endpoint:** `POST /api/auth/login`**Ejemplo de solicitud:**

```json
{
  "username": "Ronny",
  "password": "Password123!"
}
```

**Ejemplo de respuesta:**

```json
{
  "message": "Inicio de sesión exitoso",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSb25ueSIsImlhdCI6MTc0MTIzMjE0NCwiZXhwIjoxNzQxMjY4MTQ0fQ.-FB4qhVJ1Cr-scOVSU3ncafJ04gNiuQHlIGyj5p5yQU"
}
```

**🔗 Importante:** En cada petición autenticada, agregar el token en el **Header**:

```
Authorization: Bearer {token}
```

---

## 📈 Documentación de la API

Swagger está disponible en:📍 [**http://localhost:8080/swagger-ui/index.html#/**](http://localhost:8080/swagger-ui/index.html#/)

La Documenatcion en Postman en:📍 [**https://documenter.getpostman.com/view/18224934/2sAYdmk7mr**](https://documenter.getpostman.com/view/18224934/2sAYdmk7mr)
---

## 🔍 Endpoints Disponibles

### 🔒 **Autenticación**

| Método | Endpoint          | Descripción                  | Protegido |
| ------ | ----------------- | ---------------------------- | --------- |
| `POST` | `/api/auth/login` | Iniciar sesión y obtener JWT | ❌ No      |

---

### 👤 **Usuarios**

| Método   | Endpoint             | Descripción            | Protegido |
| -------- | -------------------- | ---------------------- | --------- |
| `GET`    | `/api/v1/users`      | Listar usuarios        | ✅ Sí      |
| `GET`    | `/api/v1/users/{id}` | Obtener usuario por ID | ✅ Sí      |
| `POST`   | `/api/v1/users`      | Crear nuevo usuario    | ✅ Sí      |
| `PUT`    | `/api/v1/users/{id}` | Actualizar usuario     | ✅ Sí      |
| `DELETE` | `/api/v1/users/{id}` | Eliminar usuario       | ✅ Sí      |

---

### ✅ **Tareas**

| Método   | Endpoint                                 | Descripción               | Protegido |
| -------- | ---------------------------------------- | ------------------------- | --------- |
| `GET`    | `/api/v1/tasks`                          | Listar todas las tareas   | ✅ Sí      |
| `GET`    | `/api/v1/tasks/{id}`                     | Obtener tarea por ID      | ✅ Sí      |
| `GET`    | `/api/v1/tasks/search?keyword={palabra}` | Buscar por palabra clave  | ✅ Sí      |
| `GET`    | `/api/v1/tasks/grouped-status`           | Agrupar tareas por estado | ✅ Sí      |
| `POST`   | `/api/v1/tasks`                          | Crear nueva tarea         | ✅ Sí      |
| `PUT`    | `/api/v1/tasks/{id}`                     | Actualizar tarea          | ✅ Sí      |
| `DELETE` | `/api/v1/tasks/{id}`                     | Eliminar tarea            | ✅ Sí      |

---

### 📊 **Estados de Tareas**

| Método   | Endpoint                    | Descripción              | Protegido |
| -------- |-----------------------------|--------------------------| --------- |
| `GET`    | `/api/v1/task-status`       | Listar todas las estados | ✅ Sí      |
| `GET`    | `/api/v1/task-status/{id}`  | Obtener estado por ID    | ✅ Sí      |
| `POST`   | `/api/v1/task-status`       | Crear nuevo estado       | ✅ Sí      |
| `PUT`    | `/api/v1/tasks-status/{id}` | Actualizar tarea         | ✅ Sí      |
| `DELETE` | `/api/v1/tasks-status/{id}` | Eliminar tarea           | ✅ Sí      |

---


## 🎡 Datos del Proyecto

### 💪 **Buenas Prácticas Implementadas**

- ✅ Principios **SOLID**
- ✅ Uso de **Streams y Lambdas**
- ✅ Seguridad con **JWT y Spring Security**
- ✅ Documentación con **Swagger**
- ✅ Arquitectura con **DTOs y Repositories**
- ✅ Implementación de **API First con OpenAPI**

### ✨ **Puntos Extras**

- ✅ **API First** usando `openapi-generator-maven-plugin`
- ✅ **Errores manejados globalmente** (`GlobalExceptionHandler`)
- ✅ **Código modular y reutilizable**
- ✅ **Documentación clara y concisa**


### 👨‍💻 **Autor**

- 📌 **Nombre:** `Ronny Joel Chupillon Silva`
- 📌 **Correo:** (`cronnyjoel2@gmail.com`)
- 📌 **Cargo:** `Desarrollador Full Stack Backend`
- 📌 **LinkedIn:** [Ronny Joel Chupillon Silva](https://www.linkedin.com/in/ronny-joel-chupillon-silva-874a1b226/)

