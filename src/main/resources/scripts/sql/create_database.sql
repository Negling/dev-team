CREATE TABLE roles (
  role_id    TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  role_value VARCHAR(20)      NOT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE managers (
  id         BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(20)      NOT NULL,
  last_name  VARCHAR(20)      NOT NULL UNIQUE,
  email      VARCHAR(30)      NOT NULL UNIQUE,
  phone      VARCHAR(15)      NOT NULL,
  password   VARCHAR(60)      NOT NULL,
  role_id    TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (role_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE developers (
  id             BIGINT UNSIGNED         NOT NULL AUTO_INCREMENT,
  first_name     VARCHAR(20)             NOT NULL,
  last_name      VARCHAR(20)             NOT NULL,
  email          VARCHAR(30)             NOT NULL UNIQUE,
  phone          VARCHAR(15)             NOT NULL UNIQUE,
  password       VARCHAR(60)             NOT NULL,
  specialization VARCHAR(20)             NOT NULL,
  rank           VARCHAR(20)             NOT NULL,
  hire_cost      DECIMAL(32, 2) UNSIGNED NOT NULL,
  status         VARCHAR(20)             NOT NULL,
  role_id        TINYINT UNSIGNED        NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (role_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE customers (
  id         BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(20)      NOT NULL,
  last_name  VARCHAR(20)      NOT NULL,
  email      VARCHAR(30)      NOT NULL UNIQUE,
  phone      VARCHAR(15)      NOT NULL UNIQUE,
  password   VARCHAR(60)      NOT NULL,
  role_id    TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (role_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE technical_tasks (
  id                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id        BIGINT UNSIGNED NOT NULL,
  name               VARCHAR(50)     NOT NULL,
  description        TEXT            NOT NULL,
  status             VARCHAR(20)     NOT NULL,
  manager_commentary TEXT            NULL     DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id)
  REFERENCES customers (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE technical_task_operations (
  id                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  technical_task_id BIGINT UNSIGNED NOT NULL,
  name              VARCHAR(50)     NOT NULL,
  description       TEXT            NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (technical_task_id)
  REFERENCES technical_tasks (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE requests_for_developers (
  operation_id   BIGINT UNSIGNED NOT NULL,
  specialization VARCHAR(20)     NOT NULL,
  rank           VARCHAR(20)     NOT NULL,
  quantity       TINYINT         NOT NULL,
  FOREIGN KEY (operation_id)
  REFERENCES technical_task_operations (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE projects (
  id                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  technical_task_id  BIGINT UNSIGNED NOT NULL,
  manager_id         BIGINT UNSIGNED NOT NULL,
  name               VARCHAR(50)     NOT NULL,
  description        TEXT            NOT NULL,
  manager_commentary TEXT            NULL     DEFAULT NULL,
  start_date         TIMESTAMP       NOT NULL,
  end_date           TIMESTAMP       NULL,
  status             VARCHAR(20)     NOT NULL,
  UNIQUE (technical_task_id),
  PRIMARY KEY (id),
  FOREIGN KEY (technical_task_id)
  REFERENCES technical_tasks (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (manager_id)
  REFERENCES managers (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE project_tasks (
  id           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  project_id   BIGINT UNSIGNED NOT NULL,
  operation_id BIGINT UNSIGNED NOT NULL,
  name         VARCHAR(50)     NOT NULL,
  description  TEXT            NOT NULL,
  status       VARCHAR(20)     NOT NULL DEFAULT 'NEW',
  UNIQUE (operation_id),
  PRIMARY KEY (id),
  FOREIGN KEY (project_id)
  REFERENCES projects (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (operation_id)
  REFERENCES technical_task_operations (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE task_development_data (
  task_id                  BIGINT UNSIGNED NOT NULL,
  developer_id             BIGINT UNSIGNED NOT NULL,
  developer_specialization VARCHAR(20)     NOT NULL,
  developer_rank           VARCHAR(20)     NOT NULL,
  hours_spent              INT             NOT NULL DEFAULT 0,
  status                   VARCHAR(20)     NOT NULL DEFAULT 'NEW',
  FOREIGN KEY (task_id)
  REFERENCES project_tasks (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (developer_id)
  REFERENCES developers (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE checks (
  project_id      BIGINT UNSIGNED         NOT NULL,
  customer_id     BIGINT UNSIGNED         NOT NULL,
  developers_cost DECIMAL(32, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  services        DECIMAL(32, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  taxes           DECIMAL(32, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  status          VARCHAR(20)             NOT NULL DEFAULT 'AWAITING',
  PRIMARY KEY (project_id, customer_id),
  FOREIGN KEY (project_id)
  REFERENCES projects (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (customer_id)
  REFERENCES customers (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);