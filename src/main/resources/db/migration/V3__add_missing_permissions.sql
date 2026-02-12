-- V3: Add missing permissions for USER, TRUCK, ROUTE
-- These permissions are required by @PreAuthorize in UserService, TruckService, RouteService
-- but were missing from V2__init_roles_permissions.sql

INSERT INTO permission (code, name, description) VALUES
-- USER
('CREATE_USER', 'Create user', NULL),
('VIEW_USER', 'View user', NULL),
('UPDATE_USER', 'Update user', NULL),
('UPDATE_STATUS_USER', 'Update user status', NULL),
('DELETE_USER', 'Delete user', NULL),
-- TRUCK
('CREATE_TRUCK', 'Create truck', NULL),
('VIEW_TRUCK', 'View truck', NULL),
('UPDATE_TRUCK', 'Update truck', NULL),
('UPDATE_STATUS_TRUCK', 'Update truck status', NULL),
('DELETE_TRUCK', 'Delete truck', NULL),
-- ROUTE
('CREATE_ROUTE', 'Create route', NULL),
('VIEW_ROUTE', 'View route', NULL),
('UPDATE_ROUTE', 'Update route', NULL),
('DELETE_ROUTE', 'Delete route', NULL);

-- Grant all new permissions to ADMIN role
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
JOIN permission p ON p.code IN (
    'CREATE_USER','VIEW_USER','UPDATE_USER','UPDATE_STATUS_USER','DELETE_USER',
    'CREATE_TRUCK','VIEW_TRUCK','UPDATE_TRUCK','UPDATE_STATUS_TRUCK','DELETE_TRUCK',
    'CREATE_ROUTE','VIEW_ROUTE','UPDATE_ROUTE','DELETE_ROUTE'
)
WHERE r.code = 'ADMIN';

-- Grant some permissions to MANAGER role
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
JOIN permission p ON p.code IN (
    'VIEW_USER',
    'VIEW_TRUCK','CREATE_TRUCK','UPDATE_TRUCK','UPDATE_STATUS_TRUCK',
    'VIEW_ROUTE','CREATE_ROUTE','UPDATE_ROUTE','DELETE_ROUTE'
)
WHERE r.code = 'MANAGER';
