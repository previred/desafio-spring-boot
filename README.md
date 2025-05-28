# API RESTful - NUEVO SPA
API RESTful para la empresa NUEVO SPA, encargada de administrar tareas y la autenticación de usuarios.

## Tecnologías y enfoques utilizados
- Java 17
- Spring Boot
- Spring Security con JWT
- H2
- JPA / Hibernate
- Maven
- OpenAPI / Swagger
- Enfoque API First

## Estructura del proyecto
```bash
nuevospa/
├── config/           # Configuración de seguridad, JWT, auditoria y OpenApi
├── exception/        # Excepciones personalizadas
├── handler/          # Manejador global de excepciones
├── mapper/           # Conversor de entidad y DTO
├── model/            # Entidades JPA
├── repository/       # Repositorios Spring Data
├── security/         # Seguridad JWT
├── service/          # Implementaciones Delegate
└── util/             # Validación JWT
```

## Configuración
1. Clonar el repositorio:
    ```bash
    git clone https://github.com/pablovaldebenito/desafio-spring-boot.git
    ```
2. Compilar y ejecutar el proyecto:
    ```bash
    mvn clean spring-boot:run
    ```
3. Ruta para acceder a Swagger
    ```bash
    http://localhost:8080/swagger-ui/index.html
    ```
4. Ruta para acceder a OpenAPI 3.0
    ```bash
    http://localhost:8080/v3/api-docs
    ```
5. Ruta y credenciales para acceder a H2
    ```bash
    http://localhost:8080/h2-console
    Driver class:  org.h2.Driver
    JDBC url    :  jdbc:h2:mem:nuevospadb
    User Name   :  sa
    Password    :  (vacio)
    ```

## Endpoints
| Método  | Ruta           | Descripción                            |
| ---     | ---            | ---                                    |
| POST    | `/auth/login`  | Genera token JWT                       |
| GET     | `/tareas`      | Retorna todas las tareas activas       |
| GET     | `/tareas/{id}` | Retorna la tarea activa según el id    |
| POST    | `/tareas`      | Crea una nueva tarea                   |
| PUT     | `/tareas/{id}` | Actualiza una tarea existente          |
| DELETE  | `/tareas/{id}` | Elimina una tarea                      |

## Pruebas
Considerar el archivo *nuevospa-api.postman_collection* en la ruta *api-nuevospa\postman*.

Se debe importar el archivo en Postman, en donde se encontrarán los metodos y sus respectivos ejemplos de distintos casos (exitoso, inválido, inválido debido a registro inactivo, entre otros).
- Estados Tareas precargados:
    - Borrador / id 1 / Inactiva
    - Pendiente / id 2
    - En Proceso / id 3
    - Completada / id 4
- Usuarios precargados:
    - pablo@gmail.com / id 1
    - juan@gmail.com / id 2
    - daniel@gmail.com / id 3 / Inactivo

Una vez ejecutada la prueba *Login exitoso.* del metodo *POST - Genera un token para autenticar un usuario.* de */auth/login*, el token jwt se configurará automaticamente en la colección para poder probar los demás metodos de Tareas.

## Aspectos de mejora
- Incorporación de logs.
- Generación de constantes de texto para manejar las excepciones.
- Excepciones más descriptivas.
- Validación de campos para los request de manera automatizada.
- Filtrado en los repositorios de manera global para el manejo del soft delete.
- Generación de metodos para obtener datos con auditoria.
- Validación de duplicidad al momento de crear/editar una Tarea.
- Generacion de pruebas.
- Uso de Javadoc para la documentación del código.

## Autor
- Nombre: Pablo Valdebenito
- Correo: pablo.valdebenito1317@gmail.com
- Cargo: Desarrollador Java