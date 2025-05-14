-- Estados de tarea
INSERT INTO estados_tarea (id, nombre) VALUES (1, 'Pendiente');
INSERT INTO estados_tarea (id, nombre) VALUES (2, 'En Progreso');
INSERT INTO estados_tarea (id, nombre) VALUES (3, 'Completada');

-- Usuarios (con contraseñas sin encriptar por ahora, solo para desarrollo)
INSERT INTO usuarios (id, username, password, rol) VALUES (1, 'admin', 'admin123', 'ADMIN');
INSERT INTO usuarios (id, username, password, rol) VALUES (2, 'user', 'user123', 'USER');