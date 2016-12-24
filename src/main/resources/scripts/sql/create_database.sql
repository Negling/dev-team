CREATE TABLE roles (
  role_id    TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  role_value VARCHAR(20)      NOT NULL,
  PRIMARY KEY (role_id)
);

CREATE TABLE managers (
  id         BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(20)      NOT NULL,
  last_name  VARCHAR(20)      NOT NULL,
  email      VARCHAR(30)      NOT NULL,
  phone      VARCHAR(15)      NOT NULL,
  password   VARCHAR(60)      NOT NULL,
  role_id    TINYINT UNSIGNED NOT NULL,
  UNIQUE (email),
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
  email          VARCHAR(30)             NOT NULL,
  phone          VARCHAR(15)             NOT NULL,
  password       VARCHAR(60)             NOT NULL,
  specialization VARCHAR(20)             NOT NULL,
  rank           VARCHAR(20)             NOT NULL,
  hire_cost      DECIMAL(32, 2) UNSIGNED NOT NULL,
  status         VARCHAR(20)             NOT NULL,
  role_id        TINYINT UNSIGNED        NOT NULL,
  UNIQUE (email),
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
  email      VARCHAR(30)      NOT NULL,
  phone      VARCHAR(15)      NOT NULL,
  password   VARCHAR(60)      NOT NULL,
  role_id    TINYINT UNSIGNED NOT NULL,
  UNIQUE (email),
  PRIMARY KEY (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (role_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE technical_tasks (
  id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id BIGINT UNSIGNED NOT NULL,
  manager_id  BIGINT UNSIGNED NULL,
  name        VARCHAR(50)     NOT NULL,
  description TEXT            NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id)
  REFERENCES customers (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (manager_id)
  REFERENCES managers (id)
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
  id                BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  technical_task_id BIGINT UNSIGNED NOT NULL,
  customer_id       BIGINT UNSIGNED NOT NULL,
  manager_id        BIGINT UNSIGNED NOT NULL,
  name              VARCHAR(50)     NOT NULL,
  description       TEXT            NOT NULL,
  start_date        TIMESTAMP       NOT NULL,
  end_date          TIMESTAMP       NULL,
  status            VARCHAR(20)     NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (technical_task_id)
  REFERENCES technical_tasks (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (customer_id)
  REFERENCES customers (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (manager_id)
  REFERENCES managers (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE project_tasks (
  id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  project_id  BIGINT UNSIGNED NOT NULL,
  name        VARCHAR(50)     NOT NULL,
  description TEXT            NOT NULL,
  status      VARCHAR(20)     NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (project_id)
  REFERENCES projects (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE task_developers (
  task_id      BIGINT UNSIGNED NOT NULL,
  developer_id BIGINT UNSIGNED NOT NULL,
  hours_spent  INT             NOT NULL DEFAULT 0,
  status       VARCHAR(20)     NOT NULL DEFAULT 'Running',
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
  developers_cost DECIMAL(32, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  services        DECIMAL(32, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  taxes           DECIMAL(32, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  status          VARCHAR(20)             NOT NULL DEFAULT 'Awaiting',
  FOREIGN KEY (project_id)
  REFERENCES projects (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);