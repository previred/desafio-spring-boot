# Descripción

Este proyecto es una solución al desafío técnico propuesto por PREVIRED, orientado a mejorar la productividad de los equipos en NUEVO SPA mediante una plataforma de gestión de tareas. Implementa una API REST utilizando Spring Boot, autenticación JWT y diseño de la API en OpenApi 3.

# Tecnologías y Herramientas

- Java 17: Utiliza características modernas para una implementación eficiente.
- Spring Boot 2.7.18: Facilita la configuración y el despliegue de la aplicación.
- Base de Datos H2: Para persistencia de datos en desarrollo en memoria.
- JWT (JSON Web Token): Para la autenticación de usuarios.
- OpenAPI / Swagger: Para el diseño de la API.
- Maven: Para gestión de dependencias

# Características
- Autenticación de usuarios mediante JWT.
- Operaciones CRUD para la gestión de tareas.
- Usuarios y estados de tareas predefinidos y cargados inicialmente en la base de datos.
- Prueba de integración que cubre el flujo completo de autenticación y gestión de tareas.
- Al crear una tarea siempre se le asigna el usuario que la creó y se inicia con un estado inicial.
- No se puede cambiar el usuario de una tarea y se agrega que los usuarios solo puedan operar sobre sus propias tareas.


# Instrucciones de Uso

Este proyecto puede ser importado como un proyecto Maven en tu editor preferido, existe un Profile definido para desarrollo y otro para pruebas.

- Configuración Inicial: No se requiere una configuración especial, más allá de contar con las herramientas descritas en tu ambiente de trabajo. Se puede modificar los profiles correspondientes si se necesita adecuar o cambiar por ejemplo el tipo de base de datos que se está utilizando. 

- Ejecución de la prueba: Existe una prueba unica que realiza la comprobación de las distintas funcionalidades de la aplicación utilizando datos de prueba definidos en el mismo test, este puede ejecutarse de la forma: 
`mvnw test`

- Ejecución de la aplicación: Al ejecutar la aplicación se utiliza el profile dev, en este, se realiza una carga inicial que crea 2 usuaios y 3 estados de tarea, los datos se pueden encontrar en application.properties. 
La aplicación se puede levantar, por defecto en el puerto 8080, realizando:
`mvnw spring-boot:run`
y los usuarios y contrañas para realizar login son:
`Login usuario1: correo=u1@mail.com, contrasena=u1contrasena`
`Login usuario2: correo=u2@mail.com, contrasena=u2contrasena`

- Diseño de la api: Se incluye un archivo 'openapi.yaml' que contiene la definicion en OpenApi 3 y se puede ingresar en Swagger a este en https://app.swaggerhub.com/apis/RHOFFMANNLET/PREVIRED/1.0.0

- Postman: Se agrega el archivo previred.postman_collection.json, que puede ser importado en Postman para probar los endpoints cuando se ejecuta la aplicación


------------

Rodolfo Hoffmann L.
+56 9 72167393
rhoffmann.let@gmail.com



