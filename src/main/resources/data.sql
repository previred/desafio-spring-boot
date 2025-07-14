-- Insert Users
-- Passwords are encrypted using BCryptPasswordEncoder for "password"
INSERT INTO users (username, password, role, date_creation) VALUES
('admin123', '$2a$10$9DUI3BOw2L2ywMGSnbrijO5BV/YWYTKwf1fMloDezfv9C58jQulKG', 'ROLE_ADMIN', CURRENT_TIMESTAMP()),
('user123', '$2a$10$ETCGdzK63XIjoD1jnsIDDu1QpEmPYeBmBMhcQYeC1g4xti9/.N5re', 'ROLE_USER', CURRENT_TIMESTAMP());

-- Insert Task Statuses
INSERT INTO task_status (code, description, date_creation) VALUES
('PENDIENTE', 'La tarea está pendiente de completarse', CURRENT_TIMESTAMP()),
('EN PROGRESO', 'La tarea está actualmente en progreso', CURRENT_TIMESTAMP()),
('COMPLETADA', 'La tarea ha sido completada', CURRENT_TIMESTAMP());

-- Insert some example tasks
-- (Make sure user_id and status_id exist from above inserts)
--INSERT INTO tasks (code, title, description, status_id, user_id, due_date) VALUES
--('TASK-001', 'Configurar el entorno del proyecto', 'Instalar JDK, Maven, IDE y clonar el repositorio', 2, 1, '2025-07-15'),
--('TASK-002', 'Desarrollar la autenticación de usuarios', 'Implementar puntos finales de inicio de sesión y registro basados en JWT', 2, 1, '2025-07-20'),
--('TASK-003', 'Crear la API de gestión de tareas', 'Implementar operaciones CRUD para las tareas', 1, 2, '2025-07-25'),
--('TASK-004', 'Escribir pruebas unitarias para los servicios', 'Asegurarse de que las pruebas cubran los métodos de la capa de servicio', 1, 1, '2025-07-30');
