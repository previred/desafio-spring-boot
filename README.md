# Task Management API

## Overview
This is a Task Management API built with Spring Boot, implementing an API-first approach using OpenAPI. The API allows users to authenticate with JWT and perform CRUD operations on tasks.

## Prerequisites
- Java 17 or higher
- Maven
- Postman (for testing)

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd task-management/task-management
   ```

2. **Build the Project**:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```
   - The application will start on `http://localhost:8080`.

4. **Access the H2 Database Console** (Optional):
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:taskdb`
   - Username: `sa`
   - Password: (leave empty)

5. **Access Swagger UI**:
   - URL: `http://localhost:8080/swagger-ui.html`

## Testing the API
1. **Import the Postman Collection**:
   - Import the file `postman_collection.json` (located in the root directory of the project) into Postman.

2. **Authenticate**:
   - Use the `Login` request to authenticate:
     - **Method**: POST
     - **URL**: `{{baseUrl}}/api/auth/login`
     - **Body**:
       ```json
       {
           "username": "admin",
           "password": "password"
       }
       ```
     - The response will contain a JWT token, which is automatically saved in the `token` variable.

3. **Test CRUD Operations**:
   - Use the other requests (`Create Task`, `List Tasks`, `Get Task by ID`, `Update Task`, `Delete Task`) to test the API.
   - Ensure the `Authorization` header is set to `Bearer {{token}}` for all requests except `Login`.
   - For the `Delete Task` request, the API will return:
     - `204 No Content` with a header `X-Message: La tarea se encontró y se borró` if the task is found and deleted.
     - `404 Not Found` with a body `{"error": "Id de tarea no encontrado: <id>"}` if the task does not exist.
   - To avoid caching issues in Postman, all requests include the header `Cache-Control: no-cache`.

## API Endpoints
- **POST /api/auth/login**: Authenticate and obtain a JWT token.
  - **Response**: `200 OK`
  - **Example Response**:
    ```json
    {
        "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```
- **GET /api/tasks**: List all tasks.
  - **Response**: `200 OK`
  - **Example Response**:
    ```json
    [
        {
            "id": 1,
            "title": "Sample Task",
            "description": "This is a sample task",
            "status": {
                "id": 1,
                "name": "PENDING"
            },
            "userId": 1
        }
    ]
    ```
- **POST /api/tasks**: Create a new task.
  - **Response**: `201 Created`
  - **Example Request**:
    ```json
    {
        "title": "Sample Task",
        "description": "This is a sample task",
        "statusId": 1,
        "userId": 1
    }
    ```
  - **Example Response**:
    ```json
    {
        "id": 1,
        "title": "Sample Task",
        "description": "This is a sample task",
        "status": {
            "id": 1,
            "name": "PENDING"
        },
        "userId": 1
    }
    ```
- **GET /api/tasks/{id}**: Get a task by ID.
  - **Response**: `200 OK` or `404 Not Found`
  - **Example Response**:
    ```json
    {
        "id": 1,
        "title": "Sample Task",
        "description": "This is a sample task",
        "status": {
            "id": 1,
            "name": "PENDING"
        },
        "userId": 1
    }
    ```
- **PUT /api/tasks/{id}**: Update a task.
  - **Response**: `200 OK` or `404 Not Found`
  - **Example Request**:
    ```json
    {
        "title": "Updated Task",
        "description": "This is an updated task",
        "statusId": 2,
        "userId": 1
    }
    ```
  - **Example Response**:
    ```json
    {
        "id": 1,
        "title": "Updated Task",
        "description": "This is an updated task",
        "status": {
            "id": 2,
            "name": "IN_PROGRESS"
        },
        "userId": 1
    }
    ```
- **DELETE /api/tasks/{id}**: Delete a task.
  - **Response**:
    - `204 No Content` with header `X-Message: La tarea se encontró y se borró` if the task is found and deleted.
    - `404 Not Found` with body `{"error": "Id de tarea no encontrado: <id>"}` if the task does not exist.

## Notes
- The application uses an in-memory H2 database, preloaded with users (`admin` and `user`) and task statuses (`PENDING`, `IN_PROGRESS`, `COMPLETED`).
- The JWT secret and expiration are configured in `application.yml`.

## Author
- Name: Sebastian Botero Sanchez
- Email: Seboterosa@unal.edu.co
- Role: Desarrollador