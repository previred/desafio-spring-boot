CREATE TABLE estados_tarea (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) UNIQUE
);

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR (50) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE tareas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR (255),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_asignacion TIMESTAMP,
    estado_id BIGINT,
    usuario_creador_id BIGINT,
    usuario_asignado_id BIGINT,
    FOREIGN KEY (estado_id) REFERENCES estados_tarea(id),
    FOREIGN KEY (usuario_creador_id) REFERENCES usuarios(id),
    FOREIGN KEY (usuario_asignado_id) REFERENCES usuarios(id)
);