INSERT INTO user(email, rfc, nombre, password) 
VALUES ('admin@admin.com', 'RFCADMIN' ,'admin', '1234');
INSERT INTO user(email, rfc, nombre, password) 
VALUES ('roy_code@hotmail.com', 'LUPR9606236M3' ,'Rodrigo', '1234');


INSERT INTO role(description, name) VALUES ('ROLE_ADMIN', 'ADMIN');
INSERT INTO role(description, name) VALUES ('ROLE_USER', 'USER');
INSERT INTO role(description, name) VALUES ('ROLE_SUPERVISOR', 'SUPERVISOR');

INSERT INTO user_roles(user_id, role_id) VALUES ('1', '1');
INSERT INTO user_roles(user_id, role_id) VALUES ('2', '2');