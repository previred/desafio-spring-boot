# Task Management System

This project implements a task management system, offering complete authentication (registration and login) and 
user and task management capabilities through a RESTful API. The API is designed following the OpenAPI specification 
to ensure clear documentation and consistent code generation.

## Technologies Used

* Java 17 (or higher): Main programming language.
  * Spring Boot 3.5.3: Framework for rapid Java application development.
  * Spring Web: For building RESTful APIs.
  * Spring Data JPA: For database interaction through Hibernate.
  * Spring Security: For authentication and authorization (JWT).
  * Maven: Project management and build tool.
  * H2 Database: In-memory database for development and testing. It is easily configurable for other databases such as PostgreSQL or MySQL in production environments.
  * **Lombok:** Library to reduce boilerplate code.
  * **JWT (JSON Web Tokens):** For token-based security.
  * **OpenAPI:** Specification for defining the API.
  * **OpenAPI Generator Maven Plugin:** Automatically generates API interfaces and DTOs from the OpenAPI specification.
  * **Springdoc-OpenAPI:** Automatically generates and exposes API documentation (Swagger UI).

## How to Run the Project (Step by Step)?

Follow these steps to get the task management system up and running on your local machine.

### **1. Prerequisites**

* **JDK 17** (or a higher supported version) installed.
* **Maven** installed.
* An IDE such as **IntelliJ IDEA**, **Eclipse**, or **VS Code** with support for Java and Maven.

### **2. Clone the Repository**

   ```bash
   git clone https://github.com/alfredoramos2603/nuevospa.taskmanagement.restservice.git
   cd nuevospa.taskmanagement.restservice
   ```
### **3. Clone the Repository**
* Generate the OpenAPI Code and Compile
  The **openapi-generator-maven-plugin** is configured in the pom.xml to run automatically.
  And it is generated from the file: **src/main/resources/openapi/openapi.yml**

   ```bash
   mvn clean install
   ```
   * After successful execution, the generated code will be found in the target/generated-sources/src/main/java folder.

### **4. To view the H2 database via the console
URL: http://localhost:8080/h2-console/
   The connection is located at: **src/main/resources/application.yml**
   The tables are created from the file: **src/main/resources/shema.sql**
   And the data is preloaded with the file: **src/main/resources/data.sql**

   **Users preloaded in H2 database**
      username: admin123
      password: admin123
   
      username: user123
      password: user123

### **5. To view Swagger documentation and tests
   URL: http://localhost:8081/swagger-ui/index.html

### **6. Run the Backend (Spring Boot)
   From your IDE:
   Locate the main application file (e.g., TaskManagementApplication.java, which contains @SpringBootApplication),
   or also executing...

       ```bash
       mvn spring-boot:run
       ```
   The backend will start by default at http://localhost:8080

## **7. Endpoints
   The endpoint information is included in the file which is a Postman collection.
   **src/main/resources/api-postman-collection.json**

## Author

- Name: Jose Alfredo Ramos Advíncula
- Email: jose-alfredo2009@live.com
- Application for Java Backend Developer position