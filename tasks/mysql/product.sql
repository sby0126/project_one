create table tblProduct (
    pageType varchar(4) not null,
    genderType varchar(1) not NULL,
    shopType varchar(1) not null,    
    shopName varchar(20),
    texts varchar(80),
    contentUrl varchar(100) not null,
    title varchar(50),
    price INT(8),
    term varchar(15)
);

create table tblImageHash (
    pageType varchar(4) not null,
    genderType char(1) not null,
    shopType char(1) not null,
    imgUrl varchar(256) not null,
    imgId varchar(64) not null
);