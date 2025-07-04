# API de Gestión de Tareas - Documentación

Esta API permite a los clientes gestionar tareas, incluyendo la creación, consulta, actualización y eliminación de tareas con autenticación mediante JWT.
Tambien contiene una arquitectura hexagonal con capas de adaptadores rest, dominio, excepciones y infraestructura para una gestion mas eficaz y escalable.
## 🔗 URL Base

```
http://localhost:8080
```

---

## 🔐 Autenticación

Todos los endpoints de tarea requieren autenticación

**Credenciales de token:**

- **Authorization**: `Bearer token`
- **Token**: `ejemplo_token eyJhbGciOiJIUzI1NiJ9`

---

## 📘 Resumen de Endpoints

## 🔗 URL Autenticación
### OBTENER TOKEN
### POST http://localhost:8080/auth/login

Obtener el token con el usuario ya creado en el sistema

**Body de solicitud:**

```json
{
  "email": "admin",
  "password": "admin"
}
```

**Respuestas:**

- `200 OK`: Retorna token

```json
{
  "status": "OK",
  "message": "Authentication successful",
  "timestamp": "04-07-2025 16:49:43",
  "data": {
    "Bearer": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1MTY2MjE4MywiZXhwIjoxNzUxNjY1NzgzfQ.rfTJbn2fd1Dj6GWoBDybbqdkxUiturpYOrCWn7fL--8"
  }
}
```
- `401 Unauthorized`: Credenciales inválidas

---

## 🔗 URL Usuario
### CREAR USUARIO SI DESEA REALIZAR EL FLUJO DE AUTENTICACION CON SU PROPIO TOKEN
### POST http://localhost:8080/users/create
Luego de crear su usuario podria iniciar sesion obteniendo su propio token cambiando el body en el endpoint http://localhost:8080/auth/login 

**Body de solicitud:**

```json
{
  "email": "juan",
  "password": "1234"
}
```
**Respuestas:**

- `200 OK`: Crea el usuario

```json
{
  "status": "OK",
  "message": "User created successfully",
  "timestamp": "04-07-2025 16:28:24"
}
```
---

## 🔗 URL Tareas
### Se debe tener el token ingresado en authentication bearer token en los endpoint de cada tarea

**Estados de tareas ya implentados en el sistema:**

- `PENDING`
- `IN_PROGRESS`
- `COMPLETED`
- `CANCELLED`

## Crear Tarea
### POST http://localhost:8080/tasks/create

Crea una nueva tarea en el sistema.

**Authentication:**

- `Bearer token`
- `EJEMPLO TOKEN eyJhbGciOiJIUzI1NiJ9`

**Body de solicitud:**

```json
{
  "title": "New Task3",
  "description": "This is a new task3",
  "status": "IN_PROGRESS"
}
```

**Respuestas:**

- `201 Created`: 
```json
{
  "status": "OK",
  "message": "Task created successfully",
  "timestamp": "04-07-2025 16:50:12",
  "data": {
    "id": 1,
    "title": "New Task3",
    "description": "This is a new task3",
    "createdAt": "04-07-2025 16:50:12",
    "status": "IN_PROGRESS",
    "username": "admin"
  }
}
```
- `400 Bad Request`: Datos inválidos

## Buscar Tarea por Id
### GET http://localhost:8080/tasks/findById/1
Retorna la tarea con el ID especificado.

**Parámetro de ruta:**

- `id`: ID de la tarea

**Authentication:**

- `Bearer token`
- `EJEMPLO TOKEN eyJhbGciOiJIUzI1NiJ9`



 **Respuestas:**

- `200 OK`:


```json
{
  "status": "OK",
  "message": "Task found successfully",
  "timestamp": "04-07-2025 17:26:49",
  "data": {
    "id": 1,
    "title": "New Task3",
    "description": "This is a new task3",
    "createdAt": "04-07-2025 16:50:12",
    "status": "IN_PROGRESS",
    "username": "admin"
  }
}
```

- `400 Bad Request`: 

```json
{
  "status": "NOK",
  "message": "Task not found with id: 7",
  "timestamp": "04-07-2025 17:28:35"
}
```

## Obtener Lista de tareas
### GET http://localhost:8080/tasks/getAll
Busca la lista de tareas

**Authentication:**

- `Bearer token`
- `EJEMPLO TOKEN eyJhbGciOiJIUzI1NiJ9`

**Respuestas:**

- `200 OK`:


```json
{
  "status": "OK",
  "message": "Tasks found successfully",
  "timestamp": "04-07-2025 17:31:32",
  "data": [
    {
      "id": 1,
      "title": "New Task3",
      "description": "This is a new task3",
      "createdAt": "04-07-2025 16:50:12",
      "status": "IN_PROGRESS",
      "username": "admin"
    },
    {
      "id": 2,
      "title": "New Task3",
      "description": "This is a new task3",
      "createdAt": "04-07-2025 17:26:38",
      "status": "IN_PROGRESS",
      "username": "admin"
    }
  ]
}
```
- `404 Not Found`: {
```json
{
  "status": "NOK",
  "message": "No tasks found",
  "timestamp": "04-07-2025 17:32:36",
  "data": []
}
```

## Actualizar Tarea

### PUT http://localhost:8080/tasks/update/1

Actualiza los campos de una tarea mediante su ID.

**Parámetro de ruta:**

- `id`: ID de la tarea

**Authentication:**

- `Bearer token`
- `EJEMPLO TOKEN eyJhbGciOiJIUzI1NiJ9`


**Body de solicitud:**

```json
{
  "title": "test",
  "description": "test",
  "status": "CANCELLED"
}
```

**Respuestas:**

- `200 OK`: 
```json
{
    "status": "OK",
    "message": "Task updated successfully",
    "timestamp": "04-07-2025 16:29:09",
    "data": {
        "id": 1,
        "title": "test",
        "description": "test",
        "createdAt": "04-07-2025 16:29:08",
        "status": "CANCELLED",
        "username": "admin"
    }
}
```
- `400 Bad Request`: Datos inválidos
- `404 Not Found`: No se encontró la tarea


## Eliminar Tarea
### DELETE http://localhost:8080/tasks/delete/2

Elimina una tarea por su ID.

**Parámetro de ruta:**

- `id`: ID de la tarea

**Authentication:**

- `Bearer token`
- `EJEMPLO TOKEN eyJhbGciOiJIUzI1NiJ9`

**Respuestas:**

- `200 OK`:
```json
{
  "status": "OK",
  "message": "Task deleted successfully",
  "timestamp": "04-07-2025 17:32:33"
}
```
- `404 Not Found`: No se encontró la tarea

---

## 🧾 Modelos de Datos

### UserDTO

```json
{
  "email": "string",
  "password": "string"
}
```

### TaskDTO

```json
{
  "title": "string",
  "description": "string",
  "status": "PENDING | IN_PROGRESS | COMPLETED | CANCELLED"
}
```

---

## 🧪 Swagger UI

Puedes ver y probar todos los endpoints desde Swagger en:

```
http://localhost:8080/swagger-ui.html
```

---

## ℹ️ Notas

- Todas las fechas están formateadas como `dd-MM-yyyy HH:mm:ss`
- Todas las respuestas están envueltas en un objeto `ApiResponse` que incluye: `status`, `message` y `data`
