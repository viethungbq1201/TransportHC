INSERT INTO role (id, code, name, description) VALUES
    (UUID_TO_BIN(UUID()), 'ADMIN', 'Administrator', 'System administrator'),
    (UUID_TO_BIN(UUID()), 'MANAGER', 'Manager', 'Warehouse / operation manager'),
    (UUID_TO_BIN(UUID()), 'ACCOUNTANT', 'Accountant', 'Finance & salary manager'),
    (UUID_TO_BIN(UUID()), 'DRIVER', 'Driver', 'Truck driver');


INSERT INTO permission (id, code, name, description) VALUES
    -- USER
    (UUID_TO_BIN(UUID()), 'CREATE_USER', 'Create user', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_USER', 'View user', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_USER', 'Update user', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_STATUS_USER', 'Update user status', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_USER', 'Delete user', NULL),

    -- TRUCK
    (UUID_TO_BIN(UUID()), 'CREATE_TRUCK', 'Create truck', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_TRUCK', 'View truck', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_TRUCK', 'Update truck', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_STATUS_TRUCK', 'Update truck status', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_TRUCK', 'Delete truck', NULL),

    -- ROUTE
    (UUID_TO_BIN(UUID()), 'CREATE_ROUTE', 'Create route', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_ROUTE', 'View route', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_ROUTE', 'Update route', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_ROUTE', 'Delete route', NULL),

    -- CATEGORY
    (UUID_TO_BIN(UUID()), 'CREATE_CATEGORY', 'Create category', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_CATEGORY', 'View category', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_CATEGORY', 'Update category', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_CATEGORY', 'Delete category', NULL),

    -- PRODUCT
    (UUID_TO_BIN(UUID()), 'CREATE_PRODUCT', 'Create product', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_PRODUCT', 'View product', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_PRODUCT', 'Update product', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_PRODUCT', 'Delete product', NULL),

    -- INVENTORY
    (UUID_TO_BIN(UUID()), 'CREATE_INVENTORY', 'Create inventory', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_INVENTORY', 'View inventory', NULL),
    (UUID_TO_BIN(UUID()), 'FIND_INVENTORY', 'Find inventory', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_INVENTORY', 'Update inventory', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_INVENTORY', 'Delete inventory', NULL),
    (UUID_TO_BIN(UUID()), 'FILTER_INVENTORY', 'Filter inventory', NULL),
    (UUID_TO_BIN(UUID()), 'EXPORT_INVENTORY', 'Export inventory', NULL),
    (UUID_TO_BIN(UUID()), 'IMPORT_INVENTORY', 'Import inventory', NULL),

    -- COST TYPE
    (UUID_TO_BIN(UUID()), 'CREATE_COST_TYPE', 'Create cost type', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_COST_TYPE', 'View cost type', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_COST_TYPE', 'Update cost type', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_COST_TYPE', 'Delete cost type', NULL),

    -- TRANSACTION
    (UUID_TO_BIN(UUID()), 'CREATE_TRANSACTION', 'Create transaction', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_TRANSACTION', 'View transaction', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_TRANSACTION', 'Update transaction', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_TRANSACTION', 'Delete transaction', NULL),
    (UUID_TO_BIN(UUID()), 'APPROVE_TRANSACTION', 'Approve transaction', NULL),
    (UUID_TO_BIN(UUID()), 'REJECT_TRANSACTION', 'Reject transaction', NULL),

    -- TRANSACTION DETAIL
    (UUID_TO_BIN(UUID()), 'CREATE_TRANSACTION_DETAIL', 'Create transaction detail', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_TRANSACTION_DETAIL', 'View transaction detail', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_TRANSACTION_DETAIL', 'Update transaction detail', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_TRANSACTION_DETAIL', 'Delete transaction detail', NULL),

    -- SCHEDULE
    (UUID_TO_BIN(UUID()), 'CREATE_SCHEDULE', 'Create schedule', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_SCHEDULE', 'View schedule', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_SCHEDULE', 'Update schedule', NULL),
    (UUID_TO_BIN(UUID()), 'APPROVE_SCHEDULE', 'Approve schedule', NULL),
    (UUID_TO_BIN(UUID()), 'REJECT_SCHEDULE', 'Reject schedule', NULL),
    (UUID_TO_BIN(UUID()), 'END_SCHEDULE', 'End schedule', NULL),
    (UUID_TO_BIN(UUID()), 'CANCEL_SCHEDULE', 'Cancel schedule', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_SCHEDULE', 'Delete schedule', NULL),

    -- COST
    (UUID_TO_BIN(UUID()), 'CREATE_COST', 'Create cost', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_COST', 'View cost', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_COST', 'Update cost', NULL),
    (UUID_TO_BIN(UUID()), 'APPROVE_COST', 'Approve cost', NULL),
    (UUID_TO_BIN(UUID()), 'REJECT_COST', 'Reject cost', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_COST', 'Delete cost', NULL),

    -- SALARY REPORT
    (UUID_TO_BIN(UUID()), 'CREATE_1_SALARY_REPORT', 'Create one salary report', NULL),
    (UUID_TO_BIN(UUID()), 'CREATE_ALL_SALARY_REPORT', 'Create all salary reports', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_SALARY_REPORT_DETAIL', 'View salary report detail', NULL),
    (UUID_TO_BIN(UUID()), 'VIEW_SALARY_REPORT', 'View salary report', NULL),
    (UUID_TO_BIN(UUID()), 'UPDATE_SALARY_REPORT', 'Update salary report', NULL),
    (UUID_TO_BIN(UUID()), 'DELETE_SALARY_REPORT', 'Delete salary report', NULL),
    (UUID_TO_BIN(UUID()), 'APPROVE_SALARY_REPORT', 'Approve salary report', NULL);


INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
     JOIN permission p
WHERE r.code = 'ADMIN';


INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
     JOIN permission p ON p.code IN (
         'CREATE_PRODUCT','VIEW_PRODUCT','UPDATE_PRODUCT',
         'CREATE_INVENTORY','VIEW_INVENTORY','FIND_INVENTORY','UPDATE_INVENTORY',
         'FILTER_INVENTORY','EXPORT_INVENTORY','IMPORT_INVENTORY',
         'CREATE_TRANSACTION','VIEW_TRANSACTION','UPDATE_TRANSACTION','DELETE_TRANSACTION',
         'CREATE_TRANSACTION_DETAIL','VIEW_TRANSACTION_DETAIL','UPDATE_TRANSACTION_DETAIL','DELETE_TRANSACTION_DETAIL',
         'CREATE_SCHEDULE','VIEW_SCHEDULE','UPDATE_SCHEDULE','CANCEL_SCHEDULE'
    )
WHERE r.code = 'MANAGER';


INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
     JOIN permission p ON p.code IN (
         'CREATE_1_SALARY_REPORT','CREATE_ALL_SALARY_REPORT',
         'VIEW_SALARY_REPORT_DETAIL','VIEW_SALARY_REPORT',
         'UPDATE_SALARY_REPORT'
    )
WHERE r.code = 'ACCOUNTANT';


INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
     JOIN permission p ON p.code IN (
         'VIEW_PRODUCT','VIEW_SCHEDULE','END_SCHEDULE',
         'VIEW_COST_TYPE','CREATE_COST','VIEW_COST','UPDATE_COST'
    )
WHERE r.code = 'DRIVER';