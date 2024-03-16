DROP TABLE IF EXISTS USUARIOS;

DROP TABLE IF EXISTS TAREAS;
DROP TABLE IF EXISTS ESTADOS_TAREA;


CREATE TABLE USUARIOS (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(250) NOT NULL,
password VARCHAR(250) NOT NULL
);

CREATE TABLE ESTADOS_TAREA (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(250) NOT NULL,
code VARCHAR(250) NOT NULL
);

CREATE TABLE TAREAS (
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(250) NOT NULL,
description TEXT NOT NULL,
estado_id INT, 
FOREIGN KEY (estado_id) references ESTADOS_TAREA (id)

);
