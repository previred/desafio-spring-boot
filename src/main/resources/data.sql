-- DATA ESTADOS TAREA
INSERT INTO estados_tarea (nombre, descripcion, creador, fecha_creacion, actualizador, fecha_actualizacion, activo) VALUES
('Borrador', 'Tarea en borrador', 'SISTEMA', NOW(), 'SISTEMA', NOW(), FALSE),
('Pendiente', 'Tarea pendiente de procesar', 'SISTEMA', NOW(), 'SISTEMA', NOW(), TRUE),
('En Proceso', 'Tarea en proceso de trabajo', 'SISTEMA', NOW(), 'SISTEMA', NOW(), TRUE),
('Completada', 'Tarea finalizada con éxito', 'SISTEMA', NOW(), 'SISTEMA', NOW(), TRUE);

-- DATA USUARIOS
INSERT INTO usuarios (nombre, correo, contrasena, creador, fecha_creacion, actualizador, fecha_actualizacion, activo) VALUES
('Pablo Valdebenito', 'pablo@gmail.com', '$2a$12$gk3m/jmUACj5XNRdbeQvQ.ALezP3CmYZ8ShO46GJygvX3M7TedOTG', 'SISTEMA', NOW(), 'SISTEMA', NOW(), TRUE),
('Juan Perez', 'juan@gmail.com', '$2a$12$EdBitV8LbvmgJ61L2Idfyu8KMQC2NuMkJRb1sSJfGiV8X373V1NCu', 'SISTEMA', NOW(), 'SISTEMA', NOW(), TRUE),
('Daniel Diaz', 'daniel@gmail.com', '$2a$12$gk3m/jmUACj5XNRdbeQvQ.ALezP3CmYZ8ShO46GJygvX3M7TedOTG', 'SISTEMA', NOW(), 'SISTEMA', NOW(), FALSE);