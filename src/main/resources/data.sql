-- ESTADOS DE TAREA

INSERT INTO estados_tarea (id, name) VALUES (1, 'PENDING');
INSERT INTO estados_tarea (id, name) VALUES (2, 'IN_PROGRESS');
INSERT INTO estados_tarea (id, name) VALUES (3, 'COMPLETED');


-- USUARIOS PRECARGADOS
-- password: 1234 (BCrypt)

INSERT INTO usuarios (id, username, password)
VALUES (1, 'admin', '$2a$10$Ih.9RiIiaQBLBx8hnq4NRujRh.UcMeHDQvVmklfpPhgqRQg6LAaNm');

INSERT INTO usuarios (id, username, password)
VALUES (2, 'user', '$2a$10$Ih.9RiIiaQBLBx8hnq4NRujRh.UcMeHDQvVmklfpPhgqRQg6LAaNm');