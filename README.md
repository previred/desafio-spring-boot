# API de Gestión de Tareas

API REST desarrollada con Spring Boot para la gestión de tareas personales, incluyendo autenticación con JWT, historial de cambios de estado y documentación completa mediante Swagger/OpenAPI.

---

## 🚀 Tecnologías utilizadas
- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA
- H2 Database (en memoria)
- Swagger UI (springdoc-openapi)
- Bruno (colección de pruebas)
- OpenAPI 3.0 (documentación YAML)

---

## ⚙️ Requisitos
- JDK 17+
- Maven 3.8+

---

## ▶️ Cómo ejecutar el proyecto

1. Clonar o descomprimir el proyecto.
2. Desde terminal:
   
   mvn clean install
   mvn spring-boot:run
   
3. Acceder a Swagger UI:
   
   http://localhost:8080/swagger-ui.html

---

## 🔐 Autenticación (JWT)

Para acceder a los endpoints protegidos, primero debes loguearte y obtener un token JWT.

### Endpoint:
POST /auth/login

### Ejemplo de solicitud:
```json
{
  "email": "admin@tareas.com",
  "password": "admin123"
}
```

### Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Uso del token:
Agregar el token JWT en cada petición protegida:
```
Authorization: Bearer TU_TOKEN_AQUI
```

---

## 👥 Usuarios de prueba precargados

| Nombre          | Email               | Contraseña |
|-----------------|---------------------|------------|
| Administrador   | admin@tareas.com    | admin123   |
| Maria Lopez     | maria@tareas.com    | maria123   |
| Juan Perez      | juan@tareas.com     | juan123    |

---

## 📂 Documentación de la API

- **Swagger UI**  
  Visualiza y prueba todos los endpoints:  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **OpenAPI YAML**  
  Documentación formal de la API:  
  Archivo: `docs/openapi.yaml`  
  Puedes visualizarlo en: [https://editor.swagger.io](https://editor.swagger.io)

---

## 🧪 Colección de pruebas (Bruno)

La API ha sido **testeada completamente con la herramienta Bruno**, que permite gestionar peticiones HTTP de forma similar a Postman. Se incluye la colección exportada tanto en formato Bruno como Postman (generado desde Bruno, no testeado en Postman).

### ✔️ Operaciones incluidas:
1. **Solicitar token JWT**  
   - Endpoint: `POST /auth/login`  
   - Usuario: `maria@tareas.com / maria123`  
   - El token se guarda como variable `{{token}}` automáticamente con:
     ```javascript
     bru.setVar("token", res.body.token);
     ```

2. **Crear tarea** – `POST /api/tasks`  
3. **Listar tareas** – `GET /api/tasks`  
4. **Obtener tarea por ID** – `GET /api/tasks/{id}`  
5. **Actualizar tarea** – `PUT /api/tasks/{id}`  
6. **Cambiar estado** – `PATCH /api/tasks/{id}/status`  
7. **Eliminar tarea** – `DELETE /api/tasks/{id}` 

### 📂 Archivos incluidos:
- `docs/gestion-tareas-bruno-collection.json`  
- `docs/gestion-tareas-postman-collection.json` *(generado, no testeado)*

---

## 📦 Funcionalidades implementadas
- Registro y gestión de tareas personales.
- Seguridad JWT con usuarios precargados.
- Fechas de creación y actualización.
- Eliminación lógica 
- Filtros para ocultar tareas eliminadas.
- Documentación Swagger UI y OpenAPI.

---

## 🌟 Valor agregado implementado
Se implementó como **funcionalidad destacada adicional** un **historial de cambios de estado por tarea**, que permite:
- Registrar la evolución del estado de cada tarea (por ejemplo: de `PENDING` a `IN_PROGRESS` a `COMPLETED`).
- Consultar el historial directamente en cada respuesta de tarea.
- Persistir cambios con fecha y hora de cada transición de estado.

Este historial no era requerido por el desafío, y fue incluido como **mejora** para trazabilidad y auditoría.

---

## 🗂️ Base de datos
- Motor: H2 (en memoria, sin configuración adicional).
- Estados predefinidos: `PENDING`, `IN_PROGRESS`, `COMPLETED`.
- Tabla adicional: historial de cambios de estado por tarea (valor agregado).

---

## ❓ Notas adicionales
- El token JWT se gestiona automáticamente en Bruno mediante variable.
- Swagger UI permite probar todo sin Postman.
- El proyecto está completamente funcional y probado.
- Compatible con Spring Boot 3.x (Jakarta).

---

## 📬 Autor
Desarrollado por: Cristóbal Cifuentes
