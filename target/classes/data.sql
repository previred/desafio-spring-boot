INSERT INTO ESTADO_TAREA (id, descripcion) VALUES (1, 'Pendiente');
INSERT INTO ESTADO_TAREA (id, descripcion) VALUES (2, 'En_desarrollo');
INSERT INTO ESTADO_TAREA (id, descripcion) VALUES (3, 'Finalizada');

INSERT INTO USUARIO (id, nombre, clave) VALUES (1, 'Mariana', '1234');
INSERT INTO USUARIO (id, nombre, clave) VALUES (2, 'Daniel', 'abcd');
INSERT INTO USUARIO (id, nombre, clave) VALUES (3, 'Alberto', '4321');

INSERT INTO TAREA (id, titulo, descripcion, usuario_id, estado_id)
VALUES (1, 'Cliente', 'Incidencia en producción devolución de montos no corresponden', 1, 1);

INSERT INTO TAREA (id, titulo, descripcion, usuario_id, estado_id)
VALUES (2, 'Api productos', 'el json arroja datos del producto  que no son correspondientes, mejorar', 2, 2);

ALTER TABLE TAREA ALTER COLUMN id RESTART WITH 4;