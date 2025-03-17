# Task Management API

## Descripción
Task Management API es un servicio RESTful desarrollado con **Spring Boot 3.4.3**, que permite gestionar tareas de manera eficiente. Implementa autenticación con **JWT**, validaciones, manejo de excepciones y documentación con **OpenAPI/Swagger**.

## Cómo Ejecutar el Proyecto

### **Requisitos Previos**
- **Java 17**
- **Maven 3.8+**
- **Postman** (opcional para pruebas API)

### **Clonar el Proyecto**
```bash
git clone https://github.com/tu-repo/task-management-api.git
cd task-management-api
```

### **Configurar y Ejecutar la Aplicación**
Ejecuta el siguiente comando para compilar y levantar el servidor:
```bash
mvn clean package
mvn spring-boot:run
```

La API estará disponible en:
`http://localhost:8080`

---

##Documentación y Pruebas

### **Acceder a Swagger UI**
- Abre tu navegador y accede a:
  [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
- Aquí podrás probar los endpoints de la API.

### **Importar Colección de Postman**
Para realizar pruebas, importa el siguiente JSON en **Postman**:
1 Abre **Postman** → **Importar** → **Carga el archivo JSON**.
2 Ejecuta la petición **Login** (`POST /api/auth/login`).
3 Copia el token en la variable `{{token}}`.
4 Prueba los demás endpoints.

**Archivo JSON de pruebas:**  
Ubicado en `src/main/resources/Task_Management_API.postman_collection.json`

---

##Endpoints Principales

| Método | Endpoint                | Descripción |
|--------|------------------------|-------------|
| **POST** | `/api/auth/login` | Autenticación y obtención de JWT |
| **GET**  | `/api/tareas?page={page}&size={size}` | Obtener todas las tareas con paginación |
| **POST** | `/api/tareas` | Crear una nueva tarea |
| **GET**  | `/api/tareas/{id}` | Obtener una tarea por ID |
| **PUT**  | `/api/tareas/{id}` | Actualizar una tarea |
| **DELETE** | `/api/tareas/{id}` | Eliminar una tarea |

---

##JSON de Prueba
### **Ejemplo: Autenticación**
```json
{
    "username": "usuario1",
    "password": "password1"
}
```

### **Ejemplo: Crear Tarea**
```json
{
    "titulo": "Nueva tarea",
    "descripcion": "Descripción de la tarea",
    "usuario": { "id": 1 },
    "estado": { "id": 1 }
}
```


