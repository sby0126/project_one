create table tblProduct (
    pageType varchar2(4) not null,
    genderType char(1) not null,
    shopType char(1) not null,    
    shopName varchar2(20),
    texts varchar2(80),
    contentUrl varchar2(100) not null,
    title varchar2(50),
    price number(8),
    term varchar2(15)
);

create table tblImageHash (
    pageType varchar2(4) not null,
    genderType char(1) not null,
    shopType char(1) not null,
    imgUrl varchar2(256) not null,
    imgId varchar2(64) not null
);