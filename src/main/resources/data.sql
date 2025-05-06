-- Insertar estados de tarea
INSERT INTO estado_tarea (id, nombre) VALUES
                                          (1, 'PENDIENTE'),
                                          (2, 'EN_PROGRESO'),
                                          (3, 'BLOQUEADA'),
                                          (4, 'EN_REVISION'),
                                          (5, 'COMPLETADA'),
                                          (6, 'CANCELADA');

-- Insertar usuarios (contraseñas: password123, admin123)
INSERT INTO usuario (id, username, password, enabled) VALUES
                                                          (1, 'user', '$2a$12$tTfAVd1ViyYXGWQjIKkSV.aa8lWG49nLix3rUGsZH6.PML13NFcXS', true),
                                                          (2, 'admin', '$2a$12$JcWGPYBJh4LiE8VdTLZ3oOWYP8Xj/G5h0DkzlSJsD5mqyrZnc6CCy', true);

-- Insertar roles de usuario
INSERT INTO rol (id, nombre) VALUES
                                 (1, 'ROLE_USER'),
                                 (2, 'ROLE_ADMIN');

-- Asignar roles a usuarios
INSERT INTO usuario_rol (usuario_id, rol_id) VALUES
                                                 (1, 1),
                                                 (2, 1),
                                                 (2, 2);

-- Insertar algunas tareas de ejemplo
INSERT INTO tarea (id, titulo, descripcion, fecha_creacion, fecha_vencimiento, prioridad, owner_id, estado_id, etiquetas) VALUES
                                                                                                                              (1, 'Implementar autenticación JWT', 'Crear filtros y configuración para autenticación basada en JWT', CURRENT_TIMESTAMP(), DATEADD('DAY', 7, CURRENT_DATE()), 'ALTA', 2, 2, 'backend,seguridad,jwt'),
                                                                                                                              (2, 'Implementar CRUD de tareas', 'Desarrollar endpoints REST para gestión de tareas', CURRENT_TIMESTAMP(), DATEADD('DAY', 5, CURRENT_DATE()), 'MEDIA', 2, 1, 'backend,api,spring'),
                                                                                                                              (3, 'Documentar API con OpenAPI', 'Crear especificación completa y configurar Swagger UI', CURRENT_TIMESTAMP(), DATEADD('DAY', 3, CURRENT_DATE()), 'BAJA', 1, 1, 'documentacion,openapi'),
                                                                                                                              (4, 'Implementar filtros avanzados', 'Agregar filtrado por estado, prioridad y etiquetas', CURRENT_TIMESTAMP(), DATEADD('DAY', 10, CURRENT_DATE()), 'MEDIA', 1, 1, 'backend,api');