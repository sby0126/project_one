-- DB 연결
conn sys as sysdba;
conn system/admin;

-- 앞에 c##을 안붙일 수 있게 수정
alter session set "_ORACLE_SCRIPT"=true;

-- 계정 생성 및 권한 부여
create user admin identified by admin;
GRANT CONNECT, RESOURCE TO admin;

alter user admin default tablespace users quota unlimited on users;