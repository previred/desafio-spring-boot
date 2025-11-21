-- ================================================================
-- TASK MANAGEMENT API - SEED DATA
-- ================================================================
-- Estados de tarea pre-cargados para el funcionamiento del sistema.
-- Los usuarios deben registrarse via POST /api/v1/auth/register
-- ================================================================

-- ----------------------------------------------------------------
-- Estados de Tarea (estados_tarea)
-- ----------------------------------------------------------------
-- Workflow basado en metodologías ágiles de desarrollo

INSERT INTO estados_tarea (id, name, description) VALUES
(1, 'TODO', 'Tarea pendiente por iniciar'),
(2, 'IN_PROGRESS', 'Tarea en desarrollo activo'),
(3, 'IN_REVIEW', 'Tarea completada, esperando revisión'),
(4, 'DONE', 'Tarea completada y aprobada'),
(5, 'BLOCKED', 'Tarea bloqueada por dependencias externas');
