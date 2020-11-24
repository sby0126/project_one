conn sys as sysdba;
create user admin identified by admin;
GRANT CONNECT, RESOURCE TO admin;