CREATE SCHEMA if not exists sch_tareas AUTHORIZATION usr_previred;


CREATE TABLE sch_tareas.usuarios (
        id uuid NOT NULL,
        nombre varchar(100) NOT NULL,
        apellido varchar(100) NULL,
        email varchar(100) NOT NULL,
        password varchar(255) NOT NULL,
        creado timestamp NULL,
        modificado timestamp NULL,
        last_login timestamp NULL,
        token varchar(512) NULL,
        is_active bool NULL DEFAULT true,
        CONSTRAINT users_email_key UNIQUE (email),
        CONSTRAINT users_pkey PRIMARY KEY (id)
);
CREATE INDEX idx_usuarios_email ON sch_tareas.usuarios USING btree (email);
CREATE INDEX idx_usuarios_token ON sch_tareas.usuarios USING btree (token);

-- Permissions

ALTER TABLE sch_tareas.usuarios OWNER TO usr_previred;
GRANT ALL ON TABLE sch_tareas.usuarios TO usr_previred;

CREATE TABLE sch_tareas.estados_tarea (
    id uuid NOT NULL,
    nombre varchar(250) NOT NULL,
    descripcion varchar(250) NULL,
    creado timestamp NULL,
    modificado timestamp NULL,
    CONSTRAINT estados_tarea_pkey PRIMARY KEY (id)
);

-- Permissions

ALTER TABLE sch_tareas.estados_tarea OWNER TO usr_previred;
GRANT ALL ON TABLE sch_tareas.estados_tarea TO usr_previred;



CREATE TABLE sch_tareas.tareas (
    id uuid NOT NULL,
    nombre varchar(250) NOT NULL,
    descripcion varchar(250) NULL,
    id_usuario uuid NOT NULL,
    id_estado_tarea uuid NOT NULL,
    creado timestamp NULL,
    modificado timestamp NULL,
    CONSTRAINT tareas_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY (id_usuario) REFERENCES sch_tareas.usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_estados_tareas FOREIGN KEY (id_estado_tarea) REFERENCES sch_tareas.estados_tarea(id) ON DELETE CASCADE

);

-- Permissions

ALTER TABLE sch_tareas.tareas OWNER TO usr_previred;
GRANT ALL ON TABLE sch_tareas.tareas TO usr_previred;
