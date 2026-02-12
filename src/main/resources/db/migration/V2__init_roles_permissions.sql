INSERT INTO role (code, name, description) VALUES
('ADMIN', 'Admin', 'Administrator'),
('MANAGER', 'Manager', 'Manager'),
('ACCOUNTANT', 'Accountant', 'Accountant'),
('DRIVER', 'Driver', 'Driver');

INSERT INTO permission (code, name, description) VALUES
-- CATEGORY
('CREATE_CATEGORY', 'Create category', NULL),
('VIEW_CATEGORY', 'View category', NULL),
('UPDATE_CATEGORY', 'Update category', NULL),
('DELETE_CATEGORY', 'Delete category', NULL),
-- PRODUCT
('CREATE_PRODUCT', 'Create product', NULL),
('VIEW_PRODUCT', 'View product', NULL),
('UPDATE_PRODUCT', 'Update product', NULL),
('DELETE_PRODUCT', 'Delete product', NULL),
-- INVENTORY
('CREATE_INVENTORY', 'Create inventory', NULL),
('VIEW_INVENTORY', 'View inventory', NULL),
('FIND_INVENTORY', 'Find inventory', NULL),
('UPDATE_INVENTORY', 'Update inventory', NULL),
('DELETE_INVENTORY', 'Delete inventory', NULL),
('FILTER_INVENTORY', 'Filter inventory', NULL),
('EXPORT_INVENTORY', 'Export inventory', NULL),
('IMPORT_INVENTORY', 'Import inventory', NULL),
-- COST TYPE
('CREATE_COST_TYPE', 'Create cost type', NULL),
('VIEW_COST_TYPE', 'View cost type', NULL),
('UPDATE_COST_TYPE', 'Update cost type', NULL),
('DELETE_COST_TYPE', 'Delete cost type', NULL),
-- TRANSACTION
('CREATE_TRANSACTION', 'Create transaction', NULL),
('VIEW_TRANSACTION', 'View transaction', NULL),
('UPDATE_TRANSACTION', 'Update transaction', NULL),
('DELETE_TRANSACTION', 'Delete transaction', NULL),
('APPROVE_TRANSACTION', 'Approve transaction', NULL),
('REJECT_TRANSACTION', 'Reject transaction', NULL),
-- TRANSACTION DETAIL
('CREATE_TRANSACTION_DETAIL', 'Create transaction detail', NULL),
('VIEW_TRANSACTION_DETAIL', 'View transaction detail', NULL),
('UPDATE_TRANSACTION_DETAIL', 'Update transaction detail', NULL),
('DELETE_TRANSACTION_DETAIL', 'Delete transaction detail', NULL),
-- SCHEDULE
('CREATE_SCHEDULE', 'Create schedule', NULL),
('VIEW_SCHEDULE', 'View schedule', NULL),
('UPDATE_SCHEDULE', 'Update schedule', NULL),
('APPROVE_SCHEDULE', 'Approve schedule', NULL),
('REJECT_SCHEDULE', 'Reject schedule', NULL),
('END_SCHEDULE', 'End schedule', NULL),
('CANCEL_SCHEDULE', 'Cancel schedule', NULL),
('DELETE_SCHEDULE', 'Delete schedule', NULL),
-- COST
('CREATE_COST', 'Create cost', NULL),
('VIEW_COST', 'View cost', NULL),
('UPDATE_COST', 'Update cost', NULL),
('APPROVE_COST', 'Approve cost', NULL),
('REJECT_COST', 'Reject cost', NULL),
('DELETE_COST', 'Delete cost', NULL),
-- SALARY REPORT
('CREATE_1_SALARY_REPORT', 'Create one salary report', NULL),
('CREATE_ALL_SALARY_REPORT', 'Create all salary reports', NULL),
('VIEW_SALARY_REPORT_DETAIL', 'View salary report detail', NULL),
('VIEW_SALARY_REPORT', 'View salary report', NULL),
('UPDATE_SALARY_REPORT', 'Update salary report', NULL),
('DELETE_SALARY_REPORT', 'Delete salary report', NULL),
('APPROVE_SALARY_REPORT', 'Approve salary report', NULL);

-- Role Permissions
-- ADMIN
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
JOIN permission p ON 1=1
WHERE r.code = 'ADMIN';

-- MANAGER
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

-- ACCOUNTANT
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
JOIN permission p ON p.code IN (
    'CREATE_1_SALARY_REPORT','CREATE_ALL_SALARY_REPORT',
    'VIEW_SALARY_REPORT_DETAIL','VIEW_SALARY_REPORT',
    'UPDATE_SALARY_REPORT'
)
WHERE r.code = 'ACCOUNTANT';

-- DRIVER
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
JOIN permission p ON p.code IN (
    'VIEW_PRODUCT','VIEW_SCHEDULE','END_SCHEDULE',
    'VIEW_COST_TYPE','CREATE_COST','VIEW_COST','UPDATE_COST'
)
WHERE r.code = 'DRIVER';