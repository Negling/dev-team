INSERT INTO roles (role_value)
VALUES ('CUSTOMER');
INSERT INTO roles (role_value)
VALUES ('DEVELOPER');
INSERT INTO roles (role_value)
VALUES ('MANAGER');
INSERT INTO roles (role_value)
VALUES ('ULTRAMANAGER');
INSERT INTO roles (role_value)
VALUES ('ADMIN');

INSERT INTO managers (first_name, last_name, email, phone, password, role_id)
VALUES ('Назарий', 'Кираль', 'nskdin@gmail.com', '+380987211079',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 5);
INSERT INTO managers (first_name, last_name, email, phone, password, role_id)
VALUES ('Andrew', 'Planter', 'ultramanager@gmail.com', '+389483736636',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 4);
INSERT INTO managers (first_name, last_name, email, phone, password, role_id)
VALUES ('Sergio', 'Menesko', 'manager@gmail.com', '+380454342493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 3);


INSERT INTO customers (first_name, last_name, email, phone, password, role_id)
VALUES ('Dummy', 'User', 'test@test.test', '+380454345494',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 1);
INSERT INTO customers (first_name, last_name, email, phone, password, role_id)
VALUES ('Sergio', 'Smith', 'kole@mail.ru', '+380454345493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 1);

INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Smith', 'dev1@gmail.com', '+380464345493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'DBA', 'JUNIOR', 500.00, 'HIRED', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Karlos', 'dev2@gmail.com', '+3804541235493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'DBA', 'MIDDLE', 2500.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Vanos', 'dev3@gmail.com', '+380454312493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'DBA', 'SENIOR', 3500.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Repos', 'dev4@gmail.com', '+380454425493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'DESIGNER', 'JUNIOR', 500.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Kastenada', 'dev5@gmail.com', '+380154345493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'DESIGNER', 'MIDDLE', 1500.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Dostoevsky', 'dev6@gmail.com', '+380444345493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'DESIGNER', 'SENIOR', 2000.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Valuev', 'dev7@gmail.com', '+380454345693',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'WEB', 'JUNIOR', 500.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('Igor', 'Koval', 'dev8@gmail.com', '+380454345473',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'WEB', 'MIDDLE', 10000.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Serov', 'dev9@gmail.com', '+380454335593',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'WEB', 'SENIOR', 2000.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Konevsky', 'dev10@gmail.com', '+380451345493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'BACKEND', 'JUNIOR', 700.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('Виталий', 'Старушко', 'dev11@gmail.com', '+381453345493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'BACKEND', 'MIDDLE', 3000.00, 'AVAILABLE', 2);
INSERT INTO developers (first_name, last_name, email, phone, password, specialization, rank, hire_cost, status, role_id)
VALUES ('John', 'Karasev', 'dev12@gmail.com', '+380384345493',
        '$2a$12$kOP7FU36h/9HjLzXAoyuC.cZd8bHk5wtXc3hA4RtR60Q24Q2ii02W', 'BACKEND', 'SENIOR', 4000.00, 'AVAILABLE', 2);

INSERT INTO technical_tasks (customer_id, name, description, status)
VALUES (1, 'This is test TT.', 'Thi is simple TT description.', 'NEW');
INSERT INTO technical_tasks (customer_id, name, description, status)
VALUES (1, 'This is test TT.', 'Thi is simple TT description.', 'COMPLETE');
INSERT INTO technical_tasks (customer_id, name, description, status)
VALUES (2, 'This is test TT.', 'Thi is simple TT description.', 'RUNNING');

INSERT INTO technical_task_operations (technical_task_id, name, description)
VALUES (1, 'This is test task.', 'This is test task.');
INSERT INTO technical_task_operations (technical_task_id, name, description)
VALUES (2, 'Fix content layout.', 'Replace text about dogs by text about cats.');
INSERT INTO technical_task_operations (technical_task_id, name, description)
VALUES (3, 'Add button.', 'Add button "submit cat".');

INSERT INTO requests_for_developers (operation_id, specialization, rank, quantity)
VALUES (1, 'BACKEND', 'JUNIOR', 1);
INSERT INTO requests_for_developers (operation_id, specialization, rank, quantity)
VALUES (2, 'WEB', 'JUNIOR', 1);
INSERT INTO requests_for_developers (operation_id, specialization, rank, quantity)
VALUES (3, 'DESIGNER', 'JUNIOR', 1);


INSERT INTO projects (technical_task_id, manager_id, name, description, start_date, end_date, status)
VALUES (2, 1, 'This is test task.', 'This is test task.', '2016-12-16', '2016-12-19', 'COMPLETE');
INSERT INTO projects (technical_task_id, manager_id, name, description, start_date, end_date, status)
VALUES (3, 2, 'This is test task.', 'This is test task.', '2016-12-16', '2016-12-19', 'RUNNING');

INSERT INTO project_tasks (project_id, operation_id, name, description, status)
VALUES (1, 2, 'This is test task.', 'This is test task.', 'COMPLETE');
INSERT INTO project_tasks (project_id, operation_id, name, description, status)
VALUES (2, 3, 'This is test task.', 'This is test task.', 'RUNNING');


INSERT INTO task_development_data (task_id, developer_id, developer_specialization, developer_rank, hours_spent, status)
VALUES (1, 1, 'DBA' ,'JUNIOR', 12, 'COMPLETE');
INSERT INTO task_development_data (task_id, developer_id, developer_specialization, developer_rank, hours_spent, status)
VALUES (2, 1, 'DBA' ,'JUNIOR', 0, 'RUNNING');

INSERT INTO checks (project_id, customer_id, developers_cost, services, taxes, status)
VALUES (1, 1, 700.00, 1000.00, 340.00, 'PAID');