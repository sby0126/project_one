-- DB 연결
conn sys as sysdba;

-- 계정 생성 및 권한 부여
create user admin identified by admin;
GRANT CONNECT, RESOURCE TO admin;