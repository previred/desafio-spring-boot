-- ESTADOS
INSERT INTO estados_tarea (name) VALUES ('Pendiente');
INSERT INTO estados_tarea (name) VALUES ('En Progreso');
INSERT INTO estados_tarea (name) VALUES ('Completada');
INSERT INTO estados_tarea (name) VALUES ('Cancelada');

-- USUARIOS
INSERT INTO usuarios (username, password, full_name) VALUES
('admin',   '$2a$10$Kc5r4CmeiZwK4R2Jm1isEevdU8K1cae4N7Q1HnVDQWFUpNz5hun9y', 'Administrador General'),
--admin
--admin123
('jperez',    '$2a$10$bKvbCJtWh3yl0FxSCtf00.zN0Q5tJy3tZK6lcT4bQY7PuDOzvwY2S', 'Juan Perez'),
--jperez
--123456
('mgarcia', '$2a$10$9CGSFvZ1iN6CDfGHOe/hKuFkYrf9W6mtf8l2Z0boUEtxheXI/8A06', 'Marcia Garcia');
--mgarcia
--qwerty

-- TAREAS
INSERT INTO tareas (title, description, status_id, assigned_user_id, created_at, updated_at)
VALUES ('Configurar entorno', 'Preparar el entorno de desarrollo para el nuevo proyecto.', 1, 2, CURRENT_TIMESTAMP, NULL);

INSERT INTO tareas (title, description, status_id, assigned_user_id, created_at, updated_at)
VALUES ('Diseñar base de datos', 'Crear el diagrama ER y las entidades JPA.', 2, 3, CURRENT_TIMESTAMP, NULL);

INSERT INTO tareas (title, description, status_id, assigned_user_id, created_at, updated_at)
VALUES ('Implementar autenticación', 'Agregar Spring Security con JWT.', 1, 1, CURRENT_TIMESTAMP, NULL);

INSERT INTO tareas (title, description, status_id, assigned_user_id, created_at, updated_at)
VALUES ('Escribir documentación', 'Preparar documentación inicial para Swagger.', 3, 2, CURRENT_TIMESTAMP, NULL);

INSERT INTO tareas (title, description, status_id, assigned_user_id, created_at, updated_at)
VALUES ('Revisión de código', 'Hacer revisión del pull request #14.', 4, 3, CURRENT_TIMESTAMP, NULL);
