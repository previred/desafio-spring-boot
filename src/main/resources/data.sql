-- ================================================================
-- TASK MANAGEMENT API - SEED DATA
-- ================================================================
--
-- Este archivo contiene datos pre-cargados para facilitar el testing
-- y evaluación del sistema de gestión de tareas.
--
-- CREDENCIALES PARA TESTING:
--   Username: mgarcia, crodriguez, alopez, admin
--   Password: Password123! (para todos los usuarios)
--
-- ================================================================

-- ----------------------------------------------------------------
-- 1. Estados de Tarea (estados_tarea)
-- ----------------------------------------------------------------
-- Workflow basado en metodologías ágiles de desarrollo
-- IDs fijos para facilitar referencias y testing
-- ----------------------------------------------------------------

INSERT INTO estados_tarea (id, name, description) VALUES
(1, 'TODO', 'Tarea pendiente por iniciar'),
(2, 'IN_PROGRESS', 'Tarea en desarrollo activo'),
(3, 'IN_REVIEW', 'Tarea completada, esperando revisión'),
(4, 'DONE', 'Tarea completada y aprobada'),
(5, 'BLOCKED', 'Tarea bloqueada por dependencias externas');

-- ----------------------------------------------------------------
-- 2. Usuarios Pre-cargados (usuarios)
-- ----------------------------------------------------------------
-- Equipo de desarrollo de NUEVO SPA
-- Password: "Password123!" (BCrypt hash con strength 10)
-- Hash generado: $2a$10$N9qo8uLOickgx2ZMRZoMyIIVw4JDGOogL5OvLWXkJCJQLLhVVjTgS
-- ----------------------------------------------------------------

INSERT INTO usuarios (username, email, password, full_name) VALUES
('mgarcia', 'maria.garcia@nuevospa.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyIIVw4JDGOogL5OvLWXkJCJQLLhVVjTgS', 'María García'),
('crodriguez', 'carlos.rodriguez@nuevospa.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyIIVw4JDGOogL5OvLWXkJCJQLLhVVjTgS', 'Carlos Rodríguez'),
('alopez', 'ana.lopez@nuevospa.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyIIVw4JDGOogL5OvLWXkJCJQLLhVVjTgS', 'Ana López'),
('admin', 'admin@nuevospa.cl', '$2a$10$N9qo8uLOickgx2ZMRZoMyIIVw4JDGOogL5OvLWXkJCJQLLhVVjTgS', 'System Administrator');

-- ----------------------------------------------------------------
-- 3. Tareas de Ejemplo (tareas)
-- ----------------------------------------------------------------
-- Contexto: Desarrollo del sistema de gestión de tareas
-- Estas tareas reflejan el trabajo real del equipo en este proyecto
-- Distribuidas entre usuarios y estados para testing completo
-- ----------------------------------------------------------------

-- Tareas de María García (Team Lead) - ID usuario: 1
INSERT INTO tareas (title, description, state_id, user_id, due_date) VALUES
('Definir arquitectura del sistema', 'Diseñar estructura de 3 capas (Controller-Service-Repository) y definir contratos OpenAPI', 4, 1, '2025-01-15 18:00:00'),
('Revisar implementación de seguridad', 'Validar que JWT y Spring Security 6 estén correctamente implementados sin clases deprecadas', 2, 1, '2025-02-10 17:00:00'),
('Planificar sprint de febrero', 'Definir objetivos y distribuir tareas del próximo sprint', 1, 1, '2025-02-01 10:00:00');

-- Tareas de Carlos Rodríguez (Backend Developer) - ID usuario: 2
INSERT INTO tareas (title, description, state_id, user_id, due_date) VALUES
('Implementar endpoints CRUD de tareas', 'Desarrollar controladores REST para operaciones CRUD siguiendo nomenclatura RESTful', 4, 2, '2025-01-20 16:00:00'),
('Configurar base de datos H2', 'Setup de H2 con JPA, entities y scripts de inicialización (data.sql)', 4, 2, '2025-01-18 14:00:00'),
('Agregar validaciones con Bean Validation', 'Implementar validaciones en DTOs usando Jakarta Validation annotations', 2, 2, '2025-02-05 15:00:00'),
('Investigar problema de performance en queries', 'Analizar posibles N+1 queries en listado paginado de tareas', 5, 2, '2025-02-15 12:00:00');

-- Tareas de Ana López (Frontend Developer) - ID usuario: 3
INSERT INTO tareas (title, description, state_id, user_id, due_date) VALUES
('Integrar Swagger UI', 'Configurar SpringDoc OpenAPI para documentación interactiva de la API', 4, 3, '2025-01-22 11:00:00'),
('Crear ejemplos de requests en README', 'Documentar casos de uso comunes con ejemplos curl y Postman', 2, 3, '2025-02-08 16:00:00'),
('Diseñar mockups de interfaz de usuario', 'Crear wireframes para futura interfaz web del sistema de tareas', 1, 3, '2025-02-20 14:00:00');

-- Tareas de Admin (System Administrator) - ID usuario: 4
INSERT INTO tareas (title, description, state_id, user_id, due_date) VALUES
('Configurar pipeline CI/CD', 'Setup de GitHub Actions para build automático y ejecución de tests', 3, 4, '2025-02-01 09:00:00');

-- ================================================================
-- FIN DEL SEED DATA
-- ================================================================
