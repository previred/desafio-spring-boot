INSERT INTO sch_tareas.estados_tarea
      (id, nombre, descripcion, creado, modificado)
    VALUES
      -- UUIDs elegidos de forma determinista para usar luego
      ('11111111-1111-1111-1111-111111111111', 'PENDIENTE', 'Tarea pendiente', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
      ('22222222-2222-2222-2222-222222222222', 'COMPLETADA', 'Tarea completada', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

    -- 2) INSERT en usuarios con UUID fijo
    INSERT INTO sch_tareas.usuarios
      (id, nombre, apellido, email, password, creado, modificado, last_login, token, is_active)
    VALUES
      ('33333333-3333-3333-3333-333333333333',
       'Rudy', 'Sorto', 'rudy@example.com',
       '$2a$10$PVyg5cDNx/YOvWmMNzpfWu2znTnt9B5TwmKe3vO1ylm88tZBSu2oi', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
       NULL, TRUE);

    -- 3) INSERT en tareas referenciando los UUID anteriores
    INSERT INTO sch_tareas.tareas
      (id, nombre, descripcion, id_usuario, id_estado_tarea, creado, modificado)
    VALUES
      (
        '44444444-4444-4444-4444-444444444444',
        'Primera tarea de ejemplo',
        'Descripción de la tarea relacionada',
        -- usamos los UUID estáticos: usr 3333…, estado 1111…
        '33333333-3333-3333-3333-333333333333',
        '11111111-1111-1111-1111-111111111111',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
      );