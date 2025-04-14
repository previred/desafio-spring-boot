# 📌 Task Manager API

Esta es una API RESTful desarrollada en Java con Spring Boot para la gestión de tareas. Permite a los usuarios autenticarse, crear, listar, actualizar y cambiar el estado de sus tareas. La autenticación se maneja mediante JWT.
Además este proyecto usa una base de datos en memoria H2, la cual se inicia automáticamente.

---

## 🚀 Requisitos para ejecutar el proyecto

- Java 17
- Maven 3.9.7
- Spring Boot 3.4.4

---

## ▶️ Instrucciones para levantar la aplicación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu_usuario/task-manager.git
   ```
2. Entra al directorio del proyecto:
   ```bash
   cd task-manager
   ```
3. Ejecuta la aplicación con Maven:
   ```bash
   mvn spring-boot:run
   ```
   Por defecto se ejecuta en:  
   `http://localhost:8080`

   Para acceder a la documentacion con swagger:
   `http://localhost:8080/swagger-ui/index.html`

   Para hacer pruebas se ha incluido en este proyecto una coleccion de postman, llamado examenPrevired.postman_collection (no olvidar actualizar los header)

   **Notas:**
- Cuando el proyecto se levanta, puebla de forma automática las tablas de USUARIO y ESTADO_TAREA (resources\import.sql).
- Aunque las contraseñas están encriptadas en la base de datos (`bcrypt`), para efectos de prueba todas usan la contraseña **`admin123`** en texto plano.

| Nombre de Usuario | Email                   | Contraseña (en texto plano) | Rol     |
|-------------------|-------------------------|------------------------------|---------|
| Admin             | admin@miempresa.com     | `admin123`                   | Admin   |
| Usuario1          | usuario1@miempresa.com  | `admin123`                   | Usuario |
| Usuario2          | usuario2@miempresa.com  | `admin123`                   | Usuario |

---

## 🛠️ Acceder a la consola H2

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- User Name: `sa`
- Password:  `password`

---

## 🔐 Autenticación - Login

**Endpoint:**  
`POST http://localhost:8080/api/auth/login`

**Descripción:**  
Permite autenticar a un usuario válido y obtener un token JWT para acceder a los demás endpoints protegidos del sistema.

Usuario administrador:
- Nombre Usuario: Admin
- Password: admin123 (todos los usuarios tienen la misma contraseña)

**Requiere autenticación:**  
❌ No

**Request (JSON):**
```json
{
  "nombreUsuario": "Admin",
  "password": "admin123"
}
```

**Response (JSON):**
```json
{
    "codigo": "200",
    "descripcion": "Operación realizada con éxito",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBZG1pbiIsImlkIjoxLCJpYXQiOjE3NDQ1Nzc1NDIsImV4cCI6MTc0NDU4MTE0Mn0._N-zG6SpfwfLBfVYN6RrqUBK-sjHqsOKS4j4wVVzM6w"
}
```

**Notas:**
- El token recibido debe ser incluido como header `Authorization` en todas las siguientes peticiones protegidas, con el formato:  
  `Authorization: Bearer <token>`
- El token tiene una duración de 1hra
---

**Endpoint:**  
`POST http://localhost:8080/api/tareas/crear`

**Descripción:**  
Permite crear una tarea para el usuario indicado por su id, la tarea por defecto queda en estado PE (Pendiente)

**Requiere autenticación:**  
✅ SI

**Request (JSON):**
```json
{
  "idUsuario": 1,
  "tituloTarea": "Tarea de ejemplo",
  "descripcionTarea": "Descripción de la tarea de ejemplo"
}

```

**Response (JSON):**
```json
{
    "codigo": "200",
    "descripcion": "Operación realizada con éxito",
    "idUsuario": 1,
    "tarea": {
        "id": 7,
        "titulo": "Tarea de ejemplo",
        "descripcion": "Descripción de la tarea de ejemplo",
        "estadoTarea": {
            "id": 1,
            "nombre": "PE"
        },
        "fechaCreacion": "2025-04-13T16:52:54.096451",
        "fechaActualizacion": "2025-04-13T16:52:54.096451"
    }
}
```
**Endpoint:**  
`POST http://localhost:8080/api/tareas/actualizar`

**Descripción:**  
Permite actualizar una tarea por su id, pudiendo modificar el titulo, descripcion y estado.



**Requiere autenticación:**  
✅ SI

**Request (JSON):**
```json
{
  "idUsuario": 1,
  "tituloTarea": "Tarea de ejemplo",
  "descripcionTarea": "Descripción de la tarea de ejemplo"
}

```

**Response (JSON):**
```json
{
    "codigo": "200",
    "descripcion": "Operación realizada con éxito",
    "idUsuario": 1,
    "tarea": {
        "id": 7,
        "titulo": "Tarea de ejemplo",
        "descripcion": "Descripción de la tarea de ejemplo",
        "estadoTarea": {
            "id": 1,
            "nombre": "PE"
        },
        "fechaCreacion": "2025-04-13T16:52:54.096451",
        "fechaActualizacion": "2025-04-13T16:52:54.096451"
    }
}
```
**Notas:**
- Los estados posibles son:  

| Código | Nombre       | Descripción                                  |
|--------|--------------|----------------------------------------------|
| PE     | Pendiente    | La tarea ha sido creada pero no iniciada     |
| EP     | En Progreso  | La tarea se encuentra en desarrollo          |
| CO     | Completada   | La tarea ha sido finalizada exitosamente     |
| EL     | Eliminada    | La tarea fue marcada como eliminada lógicamente |

---

**Endpoint:**  
`POST http://localhost:8080/api/tareas/eliminar`

**Descripción:**  
Permite eliminar una tarea por su id, lo que cambia su estado a EL(Eliminada)



**Requiere autenticación:**  
✅ SI

**Request (JSON):**
```json
{
  "idTarea":3
}
```

**Response (JSON):**
```json
{
    "codigo": "200",
    "descripcion": "Operación realizada con éxito",
    "tarea": {
        "id": 3,
        "titulo": "Tarea de ejemplo",
        "descripcion": "Descripción de la tarea de ejemplo",
        "estadoTarea": {
            "id": 4,
            "nombre": "EL"
        },
        "fechaCreacion": "2025-04-13T15:18:11.16441",
        "fechaActualizacion": "2025-04-13T15:18:11.16441"
    }
}
```

**Endpoint:**  
`POST http://localhost:8080/api/tareas/listarTareaPorEstado`

**Descripción:**  
Permite listar todas las tareas según su estado, si en el campo estadoTarea se envía con valor 'ALL', lista todas las tareas sin importar su estado



**Requiere autenticación:**  
✅ SI

**Request (JSON):**
```json
{
  "estadoTarea": "ALL"
}
```

**Response (JSON):**
```json
{
    "codigo": "200",
    "descripcion": "Operación realizada con éxito",
    "listaTareas": [
        {
            "id": 1,
            "titulo": "Tarea de ejemplo",
            "descripcion": "Descripción de la tarea de ejemplo",
            "estadoTarea": {
                "id": 1,
                "nombre": "PE"
            },
            "fechaCreacion": "2025-04-13T15:18:08.275821",
            "fechaActualizacion": "2025-04-13T15:18:08.275821"
        },
        {
            "id": 2,
            "titulo": "Tarea de ejemplo",
            "descripcion": "Descripción de la tarea de ejemplo",
            "estadoTarea": {
                "id": 1,
                "nombre": "PE"
            },
            "fechaCreacion": "2025-04-13T15:18:10.229798",
            "fechaActualizacion": "2025-04-13T15:18:10.229798"
        },
        {
            "id": 3,
            "titulo": "Tarea de ejemplo",
            "descripcion": "Descripción de la tarea de ejemplo",
            "estadoTarea": {
                "id": 4,
                "nombre": "EL"
            },
            "fechaCreacion": "2025-04-13T15:18:11.16441",
            "fechaActualizacion": "2025-04-13T15:18:31.097856"
        },
        {
            "id": 4,
            "titulo": "Tarea de ejemplo",
            "descripcion": "Descripción de la tarea de ejemplo",
            "estadoTarea": {
                "id": 1,
                "nombre": "PE"
            },
            "fechaCreacion": "2025-04-13T15:18:12.055416",
            "fechaActualizacion": "2025-04-13T15:18:12.055416"
        },
        {
            "id": 5,
            "titulo": "Tarea de ejemplo",
            "descripcion": "Descripción de la tarea de ejemplo",
            "estadoTarea": {
                "id": 1,
                "nombre": "PE"
            },
            "fechaCreacion": "2025-04-13T15:18:12.891238",
            "fechaActualizacion": "2025-04-13T15:18:12.891238"
        }
    ]
}
```

## 🧰 Probar con Postman

1. Realiza una petición `POST` a `/api/auth/login` con las credenciales de un usuario válido.
2. Copia el token JWT recibido.
3. En cada petición protegida, agrega el header:
   ```
   Authorization: Bearer <token>
   ```
