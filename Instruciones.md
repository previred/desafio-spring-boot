# Proyecto Spring Boot - Gestión de Tareas

Este proyecto es una API REST para la gestión de tareas, construida con Spring Boot. Incluye autenticación JWT, base de datos en memoria H2 y documentación interactiva con Swagger UI.

---

## 🚀 Cómo levantar el proyecto



1. Descargar o Clonar el repositorio:
   ```bash
   git clone https://github.com/Jtoro2602/desafio-spring-boot.git
   ```

2. Entra al directorio del proyecto:
   ```bash
   cd desafio-spring-boot
   ```

3. Ejecuta la aplicación (usando Maven o tu IDE favorito):
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🗄️ Base de datos H2

Puedes acceder a la consola H2 para inspeccionar los datos generados en memoria:

- **URL:** [http://localhost:8081/h2-console/](http://localhost:8081/h2-console/)

---

## 📚 Documentación Swagger (y pruebas)

La API está documentada usando Swagger, permitiendo hacer pruebas desde el navegador:

- **URL:** [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

---

## 🔐 Autenticación - Obtener Token

Para obtener un token JWT válido:

- **URL:** [Swagger - Auth Controller](http://localhost:8081/swagger-ui/index.html#/auth-controller/login)

### Usuario de prueba

Este usuario ya viene preinsertado al iniciar la base de datos H2:

```json
{
  "username": "admin",
  "password": "prueba"
}
```

---

## ✅ Endpoints principales

### 🔸 Crear una tarea

- **URL:** [Swagger - Crear tarea](http://localhost:8081/swagger-ui/index.html#/tarea-controller/crear)

#### Ejemplos de Request:

```json
{
  "titulo": "Prueba 1",
  "descripcion": "Prueba 1",
  "estadoId": 1
}
```

```json
{
  "titulo": "Prueba 2",
  "descripcion": "Prueba 2",
  "estadoId": 2
}
```

```json
{
  "titulo": "Prueba 3",
  "descripcion": "Prueba 3",
  "estadoId": 3
}
```

```json
{
  "titulo": "Prueba 4",
  "descripcion": "Prueba 4",
  "estadoId": 3
}
```

```json
{
  "titulo": "Prueba 5",
  "descripcion": "Prueba 5",
  "estadoId": 1
}
```
```json
{
  "titulo": "Prueba 6",
  "descripcion": "Prueba 6",
  "estadoId": 2
}
```

---

### 📋 Listar tareas

- **URL:** [Swagger - Listar tareas](http://localhost:8081/swagger-ui/index.html#/tarea-controller/listar)

---

### 🗑️ Eliminar una tarea

- **URL:** [Swagger - Eliminar tarea](http://localhost:8081/swagger-ui/index.html#/tarea-controller/eliminar)

> Requiere ID. Ejemplo: `2`

---

### ✏️ Modificar una tarea

- **URL:** [Swagger - Actualizar tarea](http://localhost:8081/swagger-ui/index.html#/tarea-controller/actualizar)

> Requiere ID. Ejemplo: `5`

#### Ejemplo de Request:

```json
{
  "titulo": "Prueba 10",
  "descripcion": "Prueba 10",
  "estadoId": 3
}
```

---

### 🔍 Obtener tarea por ID

- **URL:** [Swagger - Obtener tarea](http://localhost:8081/swagger-ui/index.html#/tarea-controller/obtener)

> Requiere ID. Ejemplo: `5`

---



## ✍️ Autor

-  Nombre: Jonnathan Toro Jara
-  Correo: jonnathan.toro@live.com
-  Cargo al que postulo: Desarrollador Backend Java -Hf solutions
