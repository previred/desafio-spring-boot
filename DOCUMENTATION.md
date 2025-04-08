# Task Manager API

Este proyecto es nua API REST para la gestión de tareas, desarrollada con **Spring Boot**. En el marco de la postulación al cargo de desarrollador backend java.

## Descripción

La aplicación permite:

- Crear tareas
- Listar todas las tareas
- Obtener una tarea por ID
- Actualizar una tarea
- Eliminar una tarea

Incluye autenticación basada en JWT y documentación interactiva con Swagger UI.

---

## Requisitos

- Java 17+
- Maven
- Curl para probar endpoints

---

## Base de datos

La base de datos que utiliza este proyecto, es una base de datos en memoria H2. La cual se reinicia cada vez que se levanta la aplicación, borrando las tareas que se hayan generado durante esta.
Existen datos precargados, lo cuales corresponden a usuaris y estados de tarea.

#### Tabla de Usuarios

| **ID** | **Name**            | **Email**                     | **Username**      | **Password** |
|--------|---------------------|-------------------------------|-------------------|--------------|
| 1      | Pedro Picapiedra     | pedro.picapiedra@previred.cl   | pedro.picapiedra  | pass1234     |
| 2      | Pablo Marmol         | pablo.marmol@previred.cl       | pablo.marmol      | pass1234     |


#### Tabla de Estados

| **ID** | **Name**      |
|--------|---------------|
| 1      | created       |
| 2      | in_progress   |
| 3      | done          |
| 4      | cancelled     |


---

## Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/hgajardofrancino/desafio-spring-boot.git

2. Construí el proyecto:

    ```bash
    mvn clean install

3. Ejecutá la aplicación:

    ```bash
    mvn spring-boot:run

---

## Autenticación

Antes de acceder a los endpoints protegidos, es necesario estar autenticado:

### Login

```bash
    curl --location 'http://localhost:8080/api/v1/auth/login' \
        --header 'Content-Type: application/json' \
        --data '{
        "username": "pablo.marmol",
        "password": "pass1234"
        }'
```

➡ Respuesta esperada:

```json
    {
      "token": "eyJhbGciOiJIUzI1NiIsInR..."
    }
```

---

##  Endpoints principales

>  Asegurarse de tener el token y enviarlo en cada request:
> En los curl de ejemplo se debe cambiar el token existente por uno que se genere. Cada token tiene una hora de validez.

```
    -H "Authorization: Bearer <TOKEN>"
```

###  - Crear tarea

```bash
    curl --location 'http://localhost:8080/api/v1/tasks' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsby5tYXJtb2wiLCJpYXQiOjE3NDQxNDc5MTMsImV4cCI6MTc0NDIzNDMxM30.igkgoNg9S_JbvroGSAaImwZVyTdquD7nk-kbHlsXPJg' \
    --data '{
        "title":"Tarea de prueba",
        "description":"Descripción de ejemplo",
        "createdBy": "pablo.marmol"
    }'
```

### - Listar todas las tareas

```bash
  curl --location 'http://localhost:8080/api/v1/tasks' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsby5tYXJtb2wiLCJpYXQiOjE3NDQxNDc5MTMsImV4cCI6MTc0NDIzNDMxM30.igkgoNg9S_JbvroGSAaImwZVyTdquD7nk-kbHlsXPJg' \

```

### - Obtener tarea por ID

```bash
    curl --location 'http://localhost:8080/api/v1/tasks/2c18dbee-e719-4e65-abdc-2b7de95908a0' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsby5tYXJtb2wiLCJpYXQiOjE3NDQxNDc5MTMsImV4cCI6MTc0NDIzNDMxM30.igkgoNg9S_JbvroGSAaImwZVyTdquD7nk-kbHlsXPJg' \

```

### - Actualizar tarea

```bash
    curl --location --request PUT 'http://localhost:8080/api/v1/tasks/2c18dbee-e719-4e65-abdc-2b7de95908a0' \
    --header 'Content-Type: application/json' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsby5tYXJtb2wiLCJpYXQiOjE3NDQxNDc5MTMsImV4cCI6MTc0NDIzNDMxM30.igkgoNg9S_JbvroGSAaImwZVyTdquD7nk-kbHlsXPJg' \
    --data '{
        "title":"Tarea de prueba MODIFICADA",
        "description":"Otra description",
        "status": "done"
    }'
```

### - Eliminar tarea

```bash
    curl --location --request DELETE 'http://localhost:8080/api/v1/tasks/039f53b9-a349-496c-882a-a8a27e9b59e6' \
    --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYWJsby5tYXJtb2wiLCJpYXQiOjE3NDQxNDc5MTMsImV4cCI6MTc0NDIzNDMxM30.igkgoNg9S_JbvroGSAaImwZVyTdquD7nk-kbHlsXPJg' \

```

---

## Swagger UI

Podés ver la documentación completa:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## Contacto

Para consultas o sugerencias, favor escribirme a [hgajardofrancino@gmail.com](mailto:hgajardofrancinol@gmail.com).
