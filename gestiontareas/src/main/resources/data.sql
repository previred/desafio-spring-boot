-- Insertar usuarios pre-cargados en la tabla "usuarios"
INSERT INTO usuarios (id, username, password, role) VALUES
(1, 'juan.perez', 'password123', 'USER'),
(2, 'ana.gomez', 'password456', 'ADMIN'),
(3, 'carlos.ruiz', 'password789', 'USER');

-- Insertar estados de tarea pre-cargados
INSERT INTO estados_tarea (id, nombre) VALUES
(1, 'Pendiente'),
(2, 'En Progreso'),
(3, 'Completada');