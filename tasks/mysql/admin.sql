USE mysql;
SELECT HOST, user FROM user;

CREATE user 'admin' IDENTIFIED BY 'admin';
grant all privileges on *.* TO 'admin';

-- DROP DATABASE if EXISTS mydb;
-- CREATE DATABASE mydb;

-- CREATE user admin@localhost IDENTIFIED BY 'admin';

-- GRANT ALL PRIVILEGES ON mydb.* TO admin@localhost;
-- grant all privileges on *.* TO admin@localhost;

-- flush PRIVILEGES;