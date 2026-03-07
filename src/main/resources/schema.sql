-- Tabla de estados de tarea
CREATE TABLE IF NOT EXISTS estados_tarea (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    usuario_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    ultima_conexion TIMESTAMP NULL
);

-- Tabla de tareas
CREATE TABLE IF NOT EXISTS tareas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    prioridad VARCHAR(20) NOT NULL,
    estado_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_tarea_estado FOREIGN KEY (estado_id) REFERENCES estados_tarea(id),
    CONSTRAINT fk_tarea_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(usuario_id),
    CONSTRAINT chk_prioridad CHECK (prioridad IN ('BAJA', 'MEDIA', 'ALTA'))
);
