INSERT INTO TASK_STATES(status) VALUES ('VALID');
INSERT INTO TASK_STATES(status) VALUES ('INVALID');
INSERT INTO ROLES(name) VALUES ('ADMIN');
INSERT INTO ROLES(name) VALUES ('USER');
INSERT INTO USERS(name, lastname, username, password, email, role_id) VALUES ('Ariel Andres', 'Acho','aariel', '$2a$10$ItIg4BTrNWDfT31yku3AqOvIj3iecRMUn3RUk.M1UHonGbhljOJ5O', 'ariel@gmail.com', 1);
INSERT INTO TASK(name,status_id) VALUES ('task1',1);
INSERT INTO TASK(name,status_id) VALUES ('task2',2);