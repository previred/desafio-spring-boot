# Gestión de Tareas – Desafío Técnico

Este proyecto implementa una API para gestionar usuarios, tareas y sus estados, 
utilizando Java 17, Spring Boot 3.5, JWT para autenticación y documentación OpenAPI.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.x
- Spring Security + JWT
- H2 Database (en memoria)
- JPA (Hibernate)
- OpenAPI + Swagger UI
- Maven

---

## ▶️ Ejecución rápida

1. Clonar el proyecto
2. Ejecutar con:
   ```bash
   mvn clean install
   mvn spring-boot:run

3.	Acceder a Swagger UI: http://localhost:8080/swagger-ui.html

---

## ▶️ Uso de Swagger

Antes de ejecutar alguna funcionalidad, debido a la seguridad aplicada se debera iniciar sesion para obtener un token, aqui los pasos para su uso en la interface de Swagger:
1. Solicitar el token en el endpoint /login con un usuario y contraseña (de los pre-cargados, revisar src/main/resources/data.sql)
2. Copiar el token obtenido y hacer clic en el boton "Authorize" que esta arriba a la izquierda
3. Pegar el token en el campo "Value" y clic en Authorize, con esto tendra una sesion activa para el uso de los demas endpoints

---

### 📘 Postman Collection

- Ver archivo `src/main/resources/gestionTareas.json`

---

### 📘 Documentación API

Esta API cuenta con documentación automática Swagger:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI YAML: Ver archivo `src/main/resources/openapi.yml`

---

🧠 Consideraciones de diseño

- Las tareas se crean asociadas al usuario autenticado.
- El estado por defecto al crear una tarea es "PENDIENTE".
- El endpoint de actualización permite cambios parciales: si no se envían userId o statusId, se mantienen los valores existentes.