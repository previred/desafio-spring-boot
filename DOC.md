# 🗂️ Sistema de Gestión de Tareas

Este proyecto Backend desarrollado en Java con Spring Boot, cuyo objetivo es realizar CRUD de tareas.


---

## Documentación
http://localhost:8080/swagger-ui/index.html
- Los End-Point estan securizados.
- Se dejan JSON de ejemplos para cada api en RUTA: **collection_postman/payload.txt** (EN LA RAÍZ DEL PROYECTO).
- Para uso mas fácil y expedito se encuentra collection de POSTMAN en RUTA: **collection_postman/Previred.collection.json** (EN LA RAÍZ DEL PROYECTO).
---

## 🚀 Características principales

- Java 17 & Spring boot 3.4.4
- API diseñada bajo el enfoque **API First** con OpenAPI 3.0.
- Persistencia de datos utilizando **Spring Data JPA** en una base de datos **H2**.
- Crear, actualizar, leer y eliminar tareas.
- Asignar tareas a otros usuarios.
- Listar tareas por estado o por ID.
- Arquitectura hexagonal limpia para separación de responsabilidades.
- Uso del patrón **Builder** para la construcción de objetos.
- Seguridad mediante **Spring Security** y **JWT**.
- Datos insertados en la base de datos H2 para pruebas **Usuarios** y **EstadoTarea**.
- Obtencion de usuario que crea la tarea desde el token para registar quien fue el creador de la tarea.
- Pruebas Unitarias y de integracion con **JUnit 5** y **Mockito** con cobertura de lineas y branches al 100% en los casos de uso.
  
---

## 📝 Importante

- Los recursos se encuentran protegidos por un token JWT, solo el End Point /Login es público, este responde un token que debe  en Swagger para realizar las peticiones.
- Endpoint: `http://localhost:8080/login` -> POST
```json
{
  "username": "usuario1",
  "password": "admin"
}
```

- Para la creación de una tarea se requiere usar id de usuario o estado de tarea validos.
- - usuarioAsignadoId: 1, 2 o null.
- - estadoTareaId: 1, 2, 3 o null. (queda por defecto 1-Pendiente).
---

## 🧠 Arquitectura

Este proyecto implementa una **Arquitectura Hexagonal (Ports & Adapters)**, separando claramente:

- **Dominio**: lógica pura (`Tarea`, `Usuario`, `EstadoTarea`)
- **Aplicación**: casos de uso (`Manejo de Lógica`)
- **Infraestructura**: controladores REST, DTOs, persistencia (`Todo lo referente al Framework, separando el framework del dominio`)
- **Configuración**: confguraciones de dependencias.

---

## 🧱 Modelo de Dominio

### 📌 Usuario

```java
public class Usuario {
    Long id;
    String username;
    String password;
    boolean enabled;
    String role;
}
```

### 📌 Tarea

```java
public class Tarea {
    Long id;
    String nombre;
    String description;
    EstadoTarea estado;
    Usuario usuarioCreador;
    Usuario usuarioAsignado;
    LocalDateTime fechaCreacion;
    LocalDateTime fechaAsignacion;
}
```
### 📌 EstadoTarea

```java
public class Tarea {
    Long id;
    String nombre;
}
```

### Arquitectura de paquetes:
```
cl
├── previred
│   ├── desafio
│   │   ├── application
│   │   │   ├── usecases
│   │   │   |   ├── estado_tarea
│   │   │   |   ├── tarea
│   │   │   |   ├── usuario
│   │   ├── domain
│   │   │   ├── exceptions
│   │   │   ├── models
│   │   │   |   ├── constants
│   │   │   ├── ports
│   │   │   |   ├── in
│   │   │   |   |   ├── usecases
│   │   │   |   |   |   ├── estado_tarea
│   │   │   |   |   |   ├── tarea
│   │   │   |   |   |   ├── usuario
│   │   │   |   ├── out
│   │   │   |   |   ├── repositoties
│   │   ├── infrastructure
│   │   │   ├── adapters
│   │   │   |   ├── in
│   │   │   |   |   ├── rest
│   │   │   |   |   |   ├── dtos
│   │   │   |   |   |   ├── impl
│   │   │   |   ├── out
│   │   │   |   |   ├── repositories
│   │   │   ├── config
│   │   │   ├── db
│   │   │   |   ├── jpa
│   │   │   |   |   ├── entities
│   │   │   |   |   ├── repositories
│   │   ├── exceptions
│   │   |    ├── catalog
│   │   |    ├── dto
│   │   ├── security
│   │   ├── shared
│___│___|___├── annotations
