-- STATUS
INSERT INTO task_statuses (name) VALUES ('created');
INSERT INTO task_statuses (name) VALUES ('in_progress');
INSERT INTO task_statuses (name) VALUES ('done');
INSERT INTO task_statuses (name) VALUES ('cancelled');


-- USERS
INSERT INTO users (name, email, username, password)
VALUES ('Pedro Picapiedra', 'pedro.picapiedra@previred.cl','pedro.picapiedra','1234');
INSERT INTO users (name, email, username, password)
VALUES ('Pablo Marmol', 'pablo.marmol@previred.cl','pablo.marmol','1234');