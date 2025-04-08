-- STATUS
INSERT INTO task_statuses (name) VALUES ('created');
INSERT INTO task_statuses (name) VALUES ('in_progress');
INSERT INTO task_statuses (name) VALUES ('done');
INSERT INTO task_statuses (name) VALUES ('cancelled');


-- USERS
INSERT INTO users (name, email, username, password)
VALUES ('Pedro Picapiedra', 'pedro.picapiedra@previred.cl','pedro.picapiedra','$2a$10$N9qo8uLOickgx2ZMRZoMy.E423jS3qL9ClsF5YT9gM1s9lAD3bq7y');
INSERT INTO users (name, email, username, password)
VALUES ('Pablo Marmol', 'pablo.marmol@previred.cl','pablo.marmol','$2a$10$N9qo8uLOickgx2ZMRZoMy.E423jS3qL9ClsF5YT9gM1s9lAD3bq7y');