INSERT INTO users (username, password) VALUES ('Ronny', '$2a$10$U6nPPJiKVZNG76i2eDsXROAA.2scdBgKmZMzULCvEuf9XP2KlpYGK');
INSERT INTO users (username, password) VALUES ('Joel', '$2a$10$U6nPPJiKVZNG76i2eDsXROAA.2scdBgKmZMzULCvEuf9XP2KlpYGK');

INSERT INTO task_status (name) VALUES ('Pendiente');
INSERT INTO task_status (name) VALUES ('En Progreso');
INSERT INTO task_status (name) VALUES ('Completada');

INSERT INTO tasks (created_at, description, user_id, status_id) VALUES ('2025-03-01', 'Revisar el informe de ventas', 1, 1);

INSERT INTO tasks (created_at, description, user_id, status_id) VALUES ('2025-03-02', 'Actualizar la base de datos de clientes', 2, 2);

INSERT INTO tasks (created_at, description, user_id, status_id) VALUES ('2025-03-03', 'Preparar presentación para reunión', 1, 3);