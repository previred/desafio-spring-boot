# 🧩 API REST - Gestión de Tareas con JWT

API REST desarrollada con Spring Boot 3.5.x que permite gestionar tareas asociadas a usuarios autenticados mediante JWT.

---

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 3.5.x
- Spring Security
- JWT
- Spring Data JPA
- H2 Database (en memoria)
- Maven

---

## 📂 Estructura del proyecto

- `controller` → Controladores REST
- `service` → Lógica de negocio
- `repository` → Acceso a datos
- `security` → Configuración JWT y filtros
- `config` → Configuración de seguridad

---

## ⚙️ Cómo ejecutar la aplicación
Compilar
mvn clean install

Ejecutar la aplicacion arrancara en 

http://localhost:8080

Consola disponible en 
http://localhost:8080/h2-console

Configuración:

JDBC URL: jdbc:h2:mem:nuevospa

User: sa

Password: (vacío)

Autenticación JWT
1️⃣ Login
Post http://localhost:8080/auth/login

Request 

{
"username": "victor",
"password": "1234"
}

Response 
JSON
{
"token": "eyJhbGciOiJIUzI1NiJ9..."
}
 guardar token para las peticiones

todas las peticiones requieren header

Authorization: Bearer TU_TOKEN_AQUI

Crear Tarea

POST http://localhost:8080/tareas

{
"titulo": "Estudiar NoSQL ",
"descripcion": "Repasar Funciones",
"estadoId": 2
}

Lista Tareas

GET  http://localhost:8080/tareas

Actualizar tareas 
PUT  http://localhost:8080/tareas/{id}

Ejemplo
PUT http://localhost:8080/tareas/1

{
"titulo": "Estudiar NoSQL ",
"descripcion": "Repasar Funciones",
"estadoId": 2
}

Eliminar Tarea

DELETE http://localhost:8080/tareas/{id}

## 🧪 Flujo completo de prueba

Hacer login → obtener token

Crear tarea

Listar tareas

Actualizar tarea

Eliminar tarea

Seguridad

Autenticación basada en JWT

Aplicación stateless

Passwords encriptadas con BCrypt

Filtro personalizado JwtAuthenticationFilter

## 📌 Notas importantes

La base de datos es en memoria (H2), por lo tanto se reinicia cada vez que se detiene la aplicación.

Los usuarios y estados se cargan automáticamente al iniciar.

## 📈 Posibles Mejoras

Implementación de tokens de actualización de

Restricciones de puntos finales basadas en roles

Documentación de

Swagger / OpenAPI Soporte de acoplamiento de

Integración

PostgreSQL

Cobertura de pruebas con JUnit Mockito

Refactor de arquitectura hexagonal
