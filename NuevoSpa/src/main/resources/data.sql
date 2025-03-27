
insert into usuarios (code, creation_date, deleted, username, password) 
values
('GCR-09901', now(), false, 'gcr', '$2a$10$CsoMyHOmgCuK18Mr6ecNgOKz8DqJ7C1RKzt/gSNhLG9lQZIRQVyby'),
('GCR2-09901', now(), false, 'gcr2', '$2a$10$SqcuLg88xyQbJNL5Af4y7eQ4GSGApow.ArwriBKoPYM7YU6J/dgAO'),
('GCR3-09901', now(), false, 'gcr3', '$2a$10$yyeh8l7iajYL3ZbxWKjIYOOIDWqqnYoP6zJgGHwukXohmcr7wk1zC');

insert into estados_tarea (code, creation_date, deleted, name) values
('PENDING', now(), false, 'Pendiente'),
('IN_PROCESS', now(), false, 'En Progreso'),
('COMPLETED', now(), false, 'Completado');