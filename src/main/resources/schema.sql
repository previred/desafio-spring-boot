DROP TABLE IF EXISTS tareas;
DROP TABLE IF EXISTS estados_tarea;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE estados_tarea (
    id BIGINT PRIMARY KEY,
    description VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tareas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    status_id BIGINT,
    user_id BIGINT,
    created_in TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_estados_tarea FOREIGN KEY (status_id)
        REFERENCES estados_tarea(id),

    CONSTRAINT fk_tarea_usuario FOREIGN KEY (user_id)
        REFERENCES usuarios(id)
);