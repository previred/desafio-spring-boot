CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS estado_tarea (
    id BIGINT PRIMARY KEY,
    nombre VARCHAR(255)
);
