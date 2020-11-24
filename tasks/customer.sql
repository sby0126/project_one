create table tblCustomer (
    CTMID varchar2(15),
    CTMPW varchar2(64), 
    CTMNO number(8) not null,
    CTMNM varchar(15) not null,
    ADDR varchar2(30) not null,
    TEL varchar2(15),
    EMAIL varchar2(20) not null,
    IS_ADMIN char(1) default 'N', 
    JOINDATE date not null,
    SALT varchar2(16) not null,
    lastLoginTime number(13) 
    failedLoginCount number(2) 
    IS_LOCK char(1) default 'N'
);

alter table tblCustomer add constraint tblCustomer_CTMID_pk primary key(CTMID);