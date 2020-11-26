USE mysql;
SELECT HOST, user FROM user;

CREATE user 'admin' IDENTIFIED BY 'admin';
grant all privileges on *.* TO 'admin';