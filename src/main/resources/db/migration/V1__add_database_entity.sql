CREATE TABLE category
(
    category_id BINARY(16)   NOT NULL,
    name        VARCHAR(255) NULL,
    CONSTRAINT pk_category PRIMARY KEY (category_id)
);

CREATE TABLE cost
(
    cost_id                BINARY(16)   NOT NULL,
    user_cost_user_id      BINARY(16)   NULL,
    `description`          VARCHAR(255) NULL,
    price                  DECIMAL NULL,
    documentary_proof      VARCHAR(255) NULL,
    date                   date NULL,
    approve_status         VARCHAR(255) NULL,
    cost_type_cost_type_id BINARY(16)   NULL,
    schedule_schedules_id  BINARY(16)   NULL,
    approved_by_user_id    BINARY(16)   NULL,
    CONSTRAINT pk_cost PRIMARY KEY (cost_id)
);

CREATE TABLE cost_type
(
    cost_type_id BINARY(16)   NOT NULL,
    name         VARCHAR(255) NULL,
    CONSTRAINT pk_costtype PRIMARY KEY (cost_type_id)
);

CREATE TABLE invalid_token
(
    token       VARCHAR(255) NOT NULL,
    expiry_time datetime NULL,
    CONSTRAINT pk_invalidtoken PRIMARY KEY (token)
);

CREATE TABLE inventory
(
    inventory_id BINARY(16) NOT NULL,
    product_id   BINARY(16) NOT NULL,
    quantity     INT NULL,
    in_transit   INT NULL,
    up_to_date   datetime NULL,
    CONSTRAINT pk_inventory PRIMARY KEY (inventory_id)
);

CREATE TABLE permission
(
    id            BINARY(16)   NOT NULL,
    code          VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_permission PRIMARY KEY (id)
);

CREATE TABLE product
(
    product_id  BINARY(16)   NOT NULL,
    name        VARCHAR(255) NULL,
    price       DECIMAL NULL,
    category_id BINARY(16)   NULL,
    CONSTRAINT pk_product PRIMARY KEY (product_id)
);

CREATE TABLE `role`
(
    id            BINARY(16)   NOT NULL,
    code          VARCHAR(255) NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE role_permission
(
    permission_id BINARY(16) NOT NULL,
    role_id       BINARY(16) NOT NULL,
    CONSTRAINT pk_role_permission PRIMARY KEY (permission_id, role_id)
);

CREATE TABLE route
(
    route_id    BINARY(16)   NOT NULL,
    name        VARCHAR(255) NULL,
    start_point VARCHAR(255) NULL,
    end_point   VARCHAR(255) NULL,
    distance    DECIMAL NULL,
    CONSTRAINT pk_route PRIMARY KEY (route_id)
);

CREATE TABLE salary_report
(
    report_id         BINARY(16) NOT NULL,
    user_user_id      BINARY(16) NULL,
    month             date NULL,
    basic_salary      DECIMAL NULL,
    reward            DECIMAL NULL,
    advance_money     DECIMAL NULL,
    cost              DECIMAL NULL,
    total             DECIMAL NULL,
    create_by_user_id BINARY(16) NULL,
    create_at         datetime NULL,
    status            VARCHAR(255) NULL,
    CONSTRAINT pk_salaryreport PRIMARY KEY (report_id)
);

CREATE TABLE schedule
(
    schedules_id               BINARY(16)   NOT NULL,
    start_date                 date NULL,
    end_date                   date NULL,
    approve_status             VARCHAR(255) NULL,
    documentary_proof          VARCHAR(255) NULL,
    reward                     DECIMAL NULL,
    created_by                 BINARY(16)   NULL,
    approved_by                BINARY(16)   NULL,
    truck_truck_id             BINARY(16)   NULL,
    route_route_id             BINARY(16)   NULL,
    transaction_transaction_id BINARY(16)   NULL,
    CONSTRAINT pk_schedule PRIMARY KEY (schedules_id)
);

CREATE TABLE transaction
(
    transaction_id     BINARY(16)   NOT NULL,
    type               VARCHAR(255) NULL,
    approve_status     VARCHAR(255) NULL,
    date               datetime NULL,
    location           VARCHAR(255) NULL,
    note               VARCHAR(255) NULL,
    created_by_user_id BINARY(16)   NULL,
    CONSTRAINT pk_transaction PRIMARY KEY (transaction_id)
);

CREATE TABLE transaction_detail
(
    id                         BINARY(16) NOT NULL,
    transaction_transaction_id BINARY(16) NULL,
    product_product_id         BINARY(16) NULL,
    quantity_change            INT NULL,
    quantity_before            INT NULL,
    quantity_after             INT NULL,
    CONSTRAINT pk_transactiondetail PRIMARY KEY (id)
);

CREATE TABLE truck
(
    truck_id      BINARY(16)   NOT NULL,
    license_plate VARCHAR(255) NULL,
    capacity      INT NULL,
    status        VARCHAR(255) NULL,
    CONSTRAINT pk_truck PRIMARY KEY (truck_id)
);

CREATE TABLE user
(
    user_id       BINARY(16)   NOT NULL,
    username      VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    full_name     VARCHAR(255) NULL,
    address       VARCHAR(255) NULL,
    phone_number  VARCHAR(255) NULL,
    status        VARCHAR(255) NULL,
    basic_salary  DECIMAL NULL,
    advance_money DECIMAL NULL,
    is_driver     BIT(1) NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);

CREATE TABLE user_roles
(
    role_id BINARY(16) NOT NULL,
    user_id BINARY(16) NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE inventory
    ADD CONSTRAINT uc_inventory_product UNIQUE (product_id);

ALTER TABLE permission
    ADD CONSTRAINT uc_permission_code UNIQUE (code);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_code UNIQUE (code);

ALTER TABLE schedule
    ADD CONSTRAINT uc_schedule_transaction_transactionid UNIQUE (transaction_transaction_id);

ALTER TABLE user
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE cost
    ADD CONSTRAINT FK_COST_ON_APPROVEDBY_USER FOREIGN KEY (approved_by_user_id) REFERENCES user (user_id);

ALTER TABLE cost
    ADD CONSTRAINT FK_COST_ON_COSTTYPE_COSTTYPEID FOREIGN KEY (cost_type_cost_type_id) REFERENCES cost_type (cost_type_id);

ALTER TABLE cost
    ADD CONSTRAINT FK_COST_ON_SCHEDULE_SCHEDULESID FOREIGN KEY (schedule_schedules_id) REFERENCES schedule (schedules_id);

ALTER TABLE cost
    ADD CONSTRAINT FK_COST_ON_USERCOST_USER FOREIGN KEY (user_cost_user_id) REFERENCES user (user_id);

ALTER TABLE inventory
    ADD CONSTRAINT FK_INVENTORY_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (product_id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (category_id);

ALTER TABLE salary_report
    ADD CONSTRAINT FK_SALARYREPORT_ON_CREATEBY_USER FOREIGN KEY (create_by_user_id) REFERENCES user (user_id);

ALTER TABLE salary_report
    ADD CONSTRAINT FK_SALARYREPORT_ON_USER_USER FOREIGN KEY (user_user_id) REFERENCES user (user_id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_APPROVED_BY FOREIGN KEY (approved_by) REFERENCES user (user_id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_CREATED_BY FOREIGN KEY (created_by) REFERENCES user (user_id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_ROUTE_ROUTEID FOREIGN KEY (route_route_id) REFERENCES route (route_id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_TRANSACTION_TRANSACTIONID FOREIGN KEY (transaction_transaction_id) REFERENCES transaction (transaction_id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_TRUCK_TRUCKID FOREIGN KEY (truck_truck_id) REFERENCES truck (truck_id);

ALTER TABLE transaction_detail
    ADD CONSTRAINT FK_TRANSACTIONDETAIL_ON_PRODUCT_PRODUCTID FOREIGN KEY (product_product_id) REFERENCES product (product_id);

ALTER TABLE transaction_detail
    ADD CONSTRAINT FK_TRANSACTIONDETAIL_ON_TRANSACTION_TRANSACTIONID FOREIGN KEY (transaction_transaction_id) REFERENCES transaction (transaction_id);

ALTER TABLE transaction
    ADD CONSTRAINT FK_TRANSACTION_ON_CREATEDBY_USER FOREIGN KEY (created_by_user_id) REFERENCES user (user_id);

ALTER TABLE role_permission
    ADD CONSTRAINT fk_rolper_on_permission FOREIGN KEY (permission_id) REFERENCES permission (id);

ALTER TABLE role_permission
    ADD CONSTRAINT fk_rolper_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (user_id);