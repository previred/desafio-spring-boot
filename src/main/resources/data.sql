INSERT INTO users (username, password, email) VALUES
                                                  ('admin', '$2a$12$/S4vH6TnHzOKEu4L3p1JhuFFrys6fiZogws6ULG2YnsHqLxbWGqBO', 'admin@example.com'),
                                                  ('user', '$$2a$12$/S4vH6TnHzOKEu4L3p1JhuFFrys6fiZogws6ULG2YnsHqLxbWGqBO', 'user@example.com');

INSERT INTO task_status (name) VALUES
                                   ('PENDING'),
                                   ('IN_PROGRESS'),
                                   ('COMPLETED');