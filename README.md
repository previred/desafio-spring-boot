# Gestión de Tareas con Spring Boot

Este proyecto es una API RESTful para gestionar tareas, con autenticación JWT, desarrollado como parte de una prueba técnica.

##  Tecnologías utilizadas

- Java 17  
- Spring Boot 3.4.x  
- Maven  
- Base de datos H2 (en memoria)  
- Spring Security con JWT  
- OpenAPI / Swagger  
- Postman (colección para pruebas)  

## Requisitos para ejecutar

- Java 17  
- Maven  
- IDE (IntelliJ IDEA o VS Code)  
- Git  

##  Configuración del Proyecto

- Puerto por defecto: 9097
- Base de datos: H2 en memoria  
  `jdbc:h2:mem:tareasdb`
- Usuario por defecto:
  - Username: "admin"
  - Password: "12345"
- Clave secreta JWT (configurada en `application.properties`):

  jwt.secret=mi_clave_super_segura_para_jwt12345

  
##  Clase personalizada para encriptación
Se implementó una clase propia llamada GenerarHash.java, que permite codificar contraseñas de forma segura usando el algoritmo BCrypt.
Esto permite encriptar manualmente contraseñas y verificar su validez, mejorando la seguridad y control del sistema.

## Endpoints disponibles
# Swagger UI:
http://localhost:9097/swagger-ui/index.html

# OpenAPI JSON:
http://localhost:9097/v3/api-docs

# H2 Console (si está habilitada):
http://localhost:9097/h2-console

## Colección de Postman
Se incluye una colección de Postman con pruebas listas para:

- Registro de usuario
- Login y obtención de JWT
- Operaciones CRUD sobre tareas
- Autorización con roles
- Validaciones

# CREAR TOKEN (LOGIN)
POST http://localhost:9097/api/auth/login
# CREAR TAREAS 
POST http://localhost:9097/api/tareas
# LISTAR TAREAS
GET http://localhost:9097/api/tareas
# ACTUALIZAR TAREA
PUT http://localhost:9097/api/tareas/1
# ELIMINAR TAREAS
DELETE http://localhost:9097/api/tareas/1


## ¿Cómo ejecutar el proyecto?
1. Clona el repositorio:
   - git clone https://github.com/juankarlitos/desafio-spring-boot.git
2. Entra al directorio:
   - cd desafio-spring-boot
3. Ejecuta con Maven:
   - ./mvnw spring-boot:run
4. Accede a:
   - H2 Console (si la habilitas): http://localhost:9097/h2-console
   - Swagger: http://localhost:9097/swagger-ui/index.html


## Probar endpoint
Primero tienes que loguearte con el usurname ("admin") y la password que es ("12345"), el cual te dara un token y con ese token puedes autorizarte para ejecutar
los endpoint, en los endpoint del crud debes ir a la pestaña Authorization colocar el tipo de autorizacion que es Bearer Token y despues en la caja de la derecha colocas el token un avez hecho eso
puedes ejecutar pinchando en el boton azul (Send), y con eso te dara la respuesta en el body del cuerpo.

# PD: puedes ejecutar una nueva clave encriptada si asi lo deceas, en la clase GenerarHash, le das click derecho sobre la clase
y le das run GenerarHash y en la consola te generara una clave encriptada que puedes cambair en el data.sql


## Coleccion Postman
La colección exportada se encuentra en la ruta:

postman/coleccion_postman.json

Puedes importarla en Postman para probar fácilmente los endpoints de autenticación y CRUD de tareas.


## Autor
# Juan Carlos Inostroza
# jc.inostrozach@gmail.com
# Postulante a cargo Backend Java Spring Boot

