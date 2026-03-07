INSERT INTO roles (id, name) VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

INSERT INTO users (id, username, password, role_id) VALUES
    (1, 'admin', '$2a$12$hpf0MOg4RZnw1/8Kydyg9.UXcHnGThCRDkpcmQVMkvp/h2TK8I066', 1),
    (2, 'user', '$2a$12$YOmp7ptrlBncbvPZ5GtZ.OMBdwecB8p9Za7aadbQZnfeHPMZo5CD.', 2);

INSERT INTO task_status (id, name) VALUES
    (1, 'PENDING'),
    (2, 'IN_PROGRESS'),
    (3, 'COMPLETED');

INSERT INTO tasks (id, title, description, created_at, due_date, status_id) VALUES
    (1, 'Configurar seguridad', 'Implementar JWT y roles en Spring Security', CURRENT_TIMESTAMP, DATEADD('DAY', 7, CURRENT_TIMESTAMP), 1),
    (2, 'Diseñar módulo de tareas', 'Crear entidades y controladores para Task', CURRENT_TIMESTAMP, DATEADD('DAY', 10, CURRENT_TIMESTAMP), 2);

INSERT INTO user_tasks (user_id, task_id) VALUES
    (1, 1),
    (2, 1),
    (2, 2);