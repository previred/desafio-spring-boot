# 📝 Task Manager API

API RESTful para la gestión de tareas, desarrollada con **Spring Boot**, **Spring Data JPA**, **Spring Security (JWT)** y **H2 Database**.  
Incluye autenticación con JWT, manejo de tareas y estados de tarea.

---

## 🚀 Características

- 🔐 **Autenticación y autorización con JWT**
- 🧾 **CRUD completo para tareas**
- 📊 **Gestión de estados de tareas** (Pendiente, En Progreso, Completada, Cancelada)
- 🌐 **Documentación de API con Swagger / OpenAPI**
- 📝 **Logs** con Log4j2
- 🧠 **Base de datos en memoria (H2)**
- 📂 **Scripts SQL (data.sql)** para inicializar datos

---

## 🛠 Tecnologías Utilizadas

- ☕ Java 17
- 🌱 Spring Boot 3.4
- 🧰 Spring Data JPA
- 🔐 Spring Security
- 🗄 H2 Database
- 🔑 JWT (JSON Web Tokens)
- 📝 Log4j2
- 🧠 Lombok
- 📜 Swagger / OpenAPI

---

## 🧪 Configuración

### 🗄 Base de Datos H2

Se utiliza H2 en memoria por defecto para desarrollo:

```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/taskmanager-db
    driverClassName: org.h2.Driver
    username: sa
    password:
    jpa:
    hibernate:
      ddl-auto: create-drop
    defer-datasource-initialization: true
    show-sql: true
    properties:
      hibernate:
        format_sql: true
  h2:
    console:
      enabled: true
      path: /h2-console


Consola H2: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:file:./data/taskmanager-db
Usuario: sa
Contraseña: (vacía)


🔐 JWT

Propiedades de JWT en application.yml:

jwt:
  secret: WTlpJHpONUBwTDB4UjMhdlExdVQ4ZlcybUo2ZUI0Yw==
  expiration-ms: 3600000 # 1 hora

⚠️ Importante: La clave secret debe tener al menos 256 bits (32 bytes) para el algoritmo HS256.

Inicialización de Datos

El archivo src/main/resources/sql/data.sql inicializa:

✅ Estados de tarea

👤 Usuarios con contraseñas encriptadas con BCrypt

📝 Tareas de ejemplo

🌐 Endpoints Principales
Proyecto postman: /TaskManager API.postman_collection.json

🔑 Autenticación

POST /auth/login
Body:

{
  "username": "admin",
  "password": "admin123"
}


Respuesta:

{
  "token": "<JWT_TOKEN>"
}

📝 Tareas
Método	Endpoint	Descripción
GET	/tasks	Listar todas las tareas
GET	/tasks/{id}	Obtener tarea por ID
POST	/tasks	Crear nueva tarea
PUT	/tasks/{id}	Actualizar tarea existente
DELETE	/tasks/{id}	Eliminar tarea
📊 Estados de Tareas
Método	Endpoint	Descripción
GET	/task-status	Listar todos los estados de tarea

🧭 Swagger / OpenAPI

📄 Documentación interactiva:
http://localhost:8080/swagger-ui/index.html

▶️ Ejecución del Proyecto

Clonar repositorio

git clone <repo_url>
cd taskmanager


Compilar con Maven

mvn clean install


Ejecutar aplicación

mvn spring-boot:run


Acceder a la API en http://localhost:8080

📝 Logs

Los logs están configurados con Log4j2.
Puedes ajustar el nivel de logging en application.yml:

logging:
  level:
    root: info
    com.nuevo.taskmanager: debug
    org.hibernate.SQL: debug
    org.hibernate.type.descriptor.sql.BasicBinder: trace
  file:
    name: logs/app.log


✍️ Autor: Johnattan Diaz
📅 Versión: 1.0.0