-- Tabla de usuario
CREATE TABLE usuario (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
role VARCHAR(255) NOT NULL
);

-- Tabla de estados de tarea
CREATE TABLE estados_tarea (
id BIGINT PRIMARY KEY,
nombre VARCHAR(255) NOT NULL
);

-- Tabla de tareas
CREATE TABLE tareas (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
titulo VARCHAR(255) NOT NULL,
descripcion VARCHAR(1000),
usuario_id BIGINT,
estado_id BIGINT,
CONSTRAINT fk_tarea_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
CONSTRAINT fk_tarea_estado FOREIGN KEY (estado_id) REFERENCES estados_tarea(id)
);
