INSERT INTO users (id, username, password) VALUES (1, 'admin', '$2a$10$O9SF1ZlQvZWYRkPrA9CNIOxMz9oU24PhhZNg4j7o7wxYW3TNimMcO');
INSERT INTO users (id, username, password) VALUES (2, 'user', '$2a$10$O9SF1ZlQvZWYRkPrA9CNIOxMz9oU24PhhZNg4j7o7wxYW3TNimMcO');

INSERT INTO task_status (id, name) VALUES (1, 'TO_DO');
INSERT INTO task_status (id, name) VALUES (2, 'IN_PROGRESS');
INSERT INTO task_status (id, name) VALUES (3, 'DONE');

INSERT INTO tasks (title, description, due_date, status_id, user_id)
VALUES ( 'Test tarea 1', 'Descripción de tarea 1', '2025-07-10', 1, 1);
INSERT INTO tasks (title, description, due_date, status_id, user_id)
VALUES ( 'Test tarea 2', 'Descripción de tarea 2', '2025-08-01', 1, 1);
INSERT INTO tasks (title, description, due_date, status_id, user_id)
VALUES ('Trabajo en curso', 'Descripción de tarea 3', '2025-07-15', 2, 1);
INSERT INTO tasks (title, description, due_date, status_id, user_id)
VALUES ( 'Completada', 'Descripción de tarea 4', '2025-07-05', 3, 1);