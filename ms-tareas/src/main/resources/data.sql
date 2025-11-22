-- Estados de tarea
INSERT INTO estados_tarea(nombre) VALUES ('PENDIENTE');
INSERT INTO estados_tarea(nombre) VALUES ('EN_PROGRESO');
INSERT INTO estados_tarea(nombre) VALUES ('COMPLETADA');

-- Usuarios con contraseñas encriptadas
INSERT INTO usuarios(username, password, nombre_completo, email, activo)
VALUES ('admin', '$2a$10$bl1SrT8yln9RdSdn31BNPuwS/CpLfd1RUfUizLpa.b02zb0yXLvya', 'Administrador', 'admin@dominio.com', true);
INSERT INTO usuarios(username, password, nombre_completo, email, activo)
VALUES ('user1', '$2a$10$bl1SrT8yln9RdSdn31BNPuwS/CpLfd1RUfUizLpa.b02zb0yXLvya', 'Usuario Uno', 'user1@dominio.com', true);

