create table tblBoard (
    articleNO number(10),
    paretNO number(10) default 0,
    title varchar(100),
    content varchar(4000),
    imageFileName varchar2(100),
    writeDate date,
    id varchar2(20) -- FK (tblCustomer의 ID 값)
);



