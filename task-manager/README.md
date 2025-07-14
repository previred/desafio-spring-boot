# TASK API Project

# Table of Contents

- [Task API Project](#user-api-project)
  - [Features](#features)
  - [Technologies](#Technologies)
  - [Profile configuration](#profile-configuration)
  - [Flyway](#flyway)
  - [Execution](#execution)
    - [Test](#test)
    - [Production](#production)
  - [resources](#resources)


## Features

- **Registro y Autenticación de Usuarios:**  
  Permite registrar usuarios con nombre, correo, contraseña.  
  Valida credenciales, actualiza el campo `lastLogin` y genera un token JWT.
- **Validaciones y Reglas de Negocio:**
  - Validación de formato de correo y contraseña (expresiones regulares configurables).
  - Verificación de unicidad en el correo y en los números de teléfono asociados a cada usuario.
- **Seguridad:**
  - Encriptación de contraseña utilizando BCrypt.
  - Generación de token de acceso mediante JWT.
  - Integración con Spring Security.
- **Persistencia:**
  - **Producción:** Utiliza PostgreSQL, configurado para el perfil `prod`.
  - **Test/Desarrollo:** Utiliza H2 en memoria, facilitando pruebas y desarrollo local.
- **Documentación:**
  - API con Swagger/OpenAPI.
- **Contenerización y Despliegue:**
  - Dockerfile multi-stage y Docker Compose para el despliegue de la API y la base de datos en producción.


## Technologies
- **Lenguaje y Frameworks:**
    - Java 21
    - Spring Boot 3.4
- **Persistencia y Base de Datos:**
    - Spring Data JPA
    - H2 (en desarrollo, configurable para otros entornos)
    - PostgreSQL (producción)
- **Migración de Base de Datos:**
  - Flyway
- **Seguridad:**
    - Spring Security
    - JWT (usando JJWT)
- **Documentación:**
    - Springdoc OpenAPI/Swagger UI
- **Contenerización y Orquestación:**
    - Docker
    - Docker Compose
- **Build y CI/CD:**
    - Maven

## Profile configuration

La aplicación utiliza perfiles para diferenciar ambientes:

- **Ambiente Test/Desarrollo:**  
  Se utiliza H2 en memoria. La configuración se encuentra en `application-test.yaml` en la sección predeterminada.  
  La consola de H2 estará disponible en:  
  `http://localhost:8080/h2-console`

- **Ambiente Producción:**  
  Se utiliza PostgreSQL y Flyway para gestionar migraciones. Este perfil se activa mediante `SPRING_PROFILES_ACTIVE=prod`.  
  La configuración de PostgreSQL se encuentra en `application-prod.yaml`.
### Flyway
Los scripts de migración se ubican en src/main/resources/db/test/migration  y src/main/resources/db/prod/migration. Cada script debe seguir la convención de nombres de Flyway, por ejemplo:
V1__Create_usuarios_table.sql.
## Prerequisites
- JDK 21
- Maven 3.x
- Git
- Docker (opcional para despliegue)
## Execution
- ### Test

  - #### 1. Clone the Repository
  ```bash
  git clone https://github.com/previred/desafio-spring-boot.git
  cd task-manager
  ```
  - #### 2. Build
  ```
  mvn clean install
  ```
  - #### 2.1 

  ```
  cd task-manager-api-impl
  ```
  - #### 3 Run 
  To start project, run:
  ```
  mvn spring-boot:run
  ```
  API disponible en [http://localhost:8080](http://localhost:8080).
  - #### 2.3 Swagger Documentacion
    - http://localhost:8080/swagger-ui.html
  - #### 2.4 Acceder a la consola H2
    - http://localhost:8080/h2-console

      Utiliza las siguientes credenciales (por defecto):

          JDBC URL: jdbc:h2:mem:managementtask_dev_db
          Username: usr_previred
          Password: admin123

- ### Production
  - #### 1. Clone the Repository
    ```bash
    git clone clone https://github.com/previred/desafio-spring-boot.git
    cd task-manager
    ```
  - #### 2. Build and run
    ```
    docker-compose up --build -d
    ```
    API disponible en [http://localhost:8080](http://localhost:8080)

- ### Resources
  Los diagramas de la solución, colección de Postman y el script SQL los encuentras en: 
       `/task-manager/resources`

        /diagrams
        /postman-collection
        /scripts-db
        /manual-usuario
  