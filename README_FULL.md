# API de Gestión de Usuarios y Tareas

Esta API RESTful permite gestionar usuarios y tareas con autenticación JWT. Incluye funcionalidades como login, CRUD de tareas, documentación Swagger, y base de datos en memoria (H2).

---

## ✅ Tecnologías utilizadas

- Java 17
- Spring Boot 3.4.x
- Spring Security + JWT
- Spring Data JPA (H2)
- Swagger / OpenAPI
- Maven

---

## ▶️ Cómo ejecutar la aplicación

### 1. Clonar el proyecto (desde tu fork)

```bash
git clone https://github.com/tu_usuario/desafio-spring-boot.git
cd desafio-spring-boot
```

### 2. Compilar

```bash
./mvnw clean install -DskipTests
```

### 3. Ejecutar

```bash
./mvnw spring-boot:run
```

La aplicación se iniciará en:  
📍 `http://localhost:8081`

---

## 🔐 Autenticación

### Endpoint de login

```
POST /auth/login
```

### JSON de prueba:

```json
{
  "username": "admin",
  "password": "admin"
}
```

Respuesta esperada:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```

---

## 🧪 Swagger UI

Accede a la documentación interactiva en:

📍 `http://localhost:8081/swagger-ui/index.html`

1. Clic en "Authorize"
2. Ingresa el token en el siguiente formato:
   ```
   Bearer <token>
   ```

---

## 🧪 Consola H2

Accede en:  
📍 `http://localhost:8081/h2-console`

- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Contraseña: *(vacía)*

---

## 📂 Datos precargados

En `data.sql`:

- Usuario: `admin` / `admin`
- Estados: `Pendiente`, `En progreso`, `Finalizada`

---

## 🧾 Endpoints disponibles

| Método | Ruta             | Descripción                  |
|--------|------------------|------------------------------|
| POST   | /auth/login      | Login con JWT                |
| GET    | /api/tareas      | Listar tareas                |
| POST   | /api/tareas      | Crear nueva tarea            |
| PUT    | /api/tareas/{id} | Actualizar tarea             |
| DELETE | /api/tareas/{id} | Eliminar tarea               |

---

## 📦 Postman Collection

Incluido archivo: `postman_collection.json`  
Puedes importarlo en Postman para probar todos los endpoints con ejemplos listos.

---

## 👤 Autor

- **Nombre:** Evelik Quezada Muñoz  
- **Correo:** evelik.quezada@gmail.com  
- **Cargo postulado:** Desarrollador Backend Java / Spring Boot