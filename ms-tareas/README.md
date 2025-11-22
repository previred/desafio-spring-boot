# Prueba Técnica - API de Gestión de Tareas

Este proyecto consiste en una API RESTful desarrollada con **Java 21** y
**Spring Boot 3.5.x** para la gestión de tareas, incluyendo
autenticación mediante **JWT**, documentación con **OpenAPI/Swagger**, y
utilizando base de datos **H2 en memoria**.

------------------------------------------------------------------------

## 🚀 Tecnologías Utilizadas

-   Java 21
-   Spring Boot 3.5.x
-   Spring Security + JWT
-   Spring Data JPA
-   H2 Database
-   OpenAPI 3 + Swagger UI
-   Maven

------------------------------------------------------------------------

## 📂 Estructura del Proyecto

    src/
     ├── main/java/com/yoserp1/tareas
     │   ├── config        → Seguridad y configuración Swagger
     │   ├── controller    → Controladores REST
     │   ├── dto           → Clases DTO
     │   ├── exception     → Excepciones personalizadas
     │   ├── mapper        → Mappers Entity/DTO
     │   ├── jpa   	       → Interfaces JPA y repositorios
	 │   ├── security      → Configuración y filtros JWT
     │   └── service       → Interfaces y lógica de negocio
     └── resources
         ├── application.yaml → Configuración de la aplicación
		 ├── schema.sql       → Esquema de la base de datos
         └── data.sql         → Datos iniciales (usuarios/estados)

------------------------------------------------------------------------

## 🛠️ Instalación y Ejecución

### 1️⃣ Clonar el repositorio

``` bash
git clone <URL_DEL_REPO>
cd tareas-api
```

### 2️⃣ Compilar y ejecutar

``` bash
mvn clean install
mvn spring-boot:run
```

### 3️⃣ Acceder a la API y documentación

Recurso      URL
  ------------ -----------------------------------------------
Swagger UI   `http://localhost:8080/swagger-ui/index.html`
Console H2   `http://localhost:8080/h2-console`

Credenciales BD:

    jdbc:h2:mem:tareasdb
    user: sa
    password:

------------------------------------------------------------------------

## 🧪 Pruebas Unitarias

El proyecto incluye pruebas unitarias para validar la lógica de negocio de los servicios y garantizar la calidad del código.

### 📌 Tecnologías utilizadas
- **JUnit 5** (Jupiter)
- **Mockito**
- **Spring Boot Test**

### ▶️ Ejecución de pruebas

Para ejecutar todas las pruebas:

```bash
mvn test
```

------------------------------------------------------------------------

## 🔐 Autenticación

### 📍 POST /api/auth/login

``` json
{
  "username": "admin",
  "password": "123456"
}
```

📌 *Respuesta exitosa:*

``` json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5...",
  "username": "admin"
}
```

👉 Copia el token y presiona **"Authorize"** en Swagger:

    Bearer <token>

------------------------------------------------------------------------

## 📝 Ejemplos CRUD de Tareas

### 📍 GET /api/tareas

Obtiene todas las tareas.

------------------------------------------------------------------------

### 📍 POST /api/tareas

``` json
{
  "titulo": "Implementar autenticación JWT",
  "descripcion": "Desarrollar endpoint de login",
  "estado": "PENDIENTE",
  "usuarioId": 1
}
```

------------------------------------------------------------------------

### 📍 GET /api/tareas/{id}

------------------------------------------------------------------------

### 📍 PUT /api/tareas/{id}

``` json
{
  "titulo": "Actualizar autenticación",
  "estado": "COMPLETADO"
}
```

------------------------------------------------------------------------

### 📍 DELETE /api/tareas/{id}

------------------------------------------------------------------------

## 🧪 Datos precargados

### Usuarios

Usuario   Password
  --------- ----------
admin     123456 /
user1     123456

### Estados de tarea

Estado
------------
PENDIENTE /
EN_PROCESO /
COMPLETADO

------------------------------------------------------------------------

## 👤 Autor

**Yoser Perez**

------------------------------------------------------------------------

## 📄 Licencia

Este proyecto se entrega como parte de una prueba técnica.

------------------------------------------------------------------------

¡Gracias por la oportunidad! 🚀
