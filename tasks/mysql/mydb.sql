create table item                               
(
    itemcd      varchar(15) not null,
    itemnm      varchar(30) not null,
    inputdate   date,
    itemprice   int(6),
    splno       int(8),
    primary key(itemcd)	
);


create table warehouse
(
    itemcd  varchar(15) not null,
    itemqty int(5),
    cost    int(8),
    primary key(itemcd)
);


create table tradeinfo
(
    idx 		int(1) not null,
    itemcd  varchar(15) not null,
    sellqty int(5),
    splqty  int(5),
    tdprsno int(8) not null,
    tdcost  int(8) not null,
    tddate  date,
    primary key(idx)
);

create table itemorder
(
    ordno       int(10),                              -- 주문번호
    orddate     date,                                    -- 주문일자
    itemcd      varchar(15) not null,                   -- 아이템 코드
    qty         int(5),                               -- 주문 수량
    ctmid       varchar(15),                            -- 고객 id
    ctmorder    varchar(30),                             -- 고객 요청 사항
   primary key(ordno)
);

create table ctm
(
    ctmid       		varchar(15),                        -- 고객 id
    ctmpw       		varchar(20),                        -- 고객 pw
    ctmno        		int(8) not null,                  -- 고객 idx
    ctmnm       		varchar(15) not null,               -- 고객 명
    addr        		varchar(100) not null,              -- 고객 주소
    tel         		varchar(15),                        -- 고객 tel
    email       		varchar(20) not null,               -- 이메일
    zipcode     		char(5),                             -- 우편번호
    is_admin    		char(1) default 'N',                 -- 관리자 구분
    joindate    		date,                                -- 가입일시
    salt        		varchar(16) not null,               -- 해시 기초값
    last_login  		int(13),                          -- 최근 시도된 로그인 시간정보 (sec까지)
    failed_login_cnt int(2),                      -- 로그인 실패 횟수
    is_lock     		char(1) default 'N',                  -- 휴면 계정 여부
    primary key(ctmid)
);

create table spl
(
    splno   int(8) not null,
    splnm   varchar(15),
    itemcd  varchar(15) not null,
    spladdr varchar(30),
    primary key (splno)
);

create table bbs
(
    ctxtno  int(10) not NULL AUTO_INCREMENT,        -- 게시글 번호
    wrtidx  int(1) not null,         -- idx값을 통해 작성자의 타입을 분류
    wrtno   int(5) not null,         -- 작성자 번호
    wrtnm   varchar(20),               -- 작성자 명
    ctitle  varchar(50),               -- 게시물 명
    ctxt    varchar(500),              -- 게시물 내용
    pos     int(2),                  -- 게시글의 상대 깊이값
    ref     int(2),                  -- 답글일 경우의 소속 게시글 번호
    depth   int(2),                  -- 글의 깊이값
    cpwd    varchar(20) not null,      -- 게시글 비밀번호
    reply   int(5),                  -- 게시물 리플 수
    viewcnt int(6),                  -- 게시물 뷰    
    wrtdate date,                        -- 작성 일자
    primary key (ctxtno)
);

create table bbsreply
(
    rpyno    int(10) not NULL AUTO_INCREMENT,        -- 답글 번호
--  rplrno   int(5) not null,         -- 답글 작성자 번호
    rplrnm   varchar(20),               -- 답글 작성자 명
    ctxtno   int(10),                 -- 부모 글 번호
    rpydepth int(2),                  -- 답글 깊이값
    rpyctxt  varchar(100),              -- 답글 내용
    rpypwd   varchar(20) not null,      -- 답글 비밀번호
    rpydate  date,                        -- 답글 작성 일자
    primary key (rpyno)    
);

create table adminbbs
(
    ctxtno      INT(10) not NULL AUTO_INCREMENT,
    wrtno       varchar(8) not null,
    wrtnm       varchar(20),
    ctitle      varchar(50),
    ctxt        varchar(500),
    cpwd        varchar(20),
    filenm      varchar(20),
    filesize    int(15),
    wrtdate     date,
    primary key (ctxtno)
);

create table tblProduct
(
    pageType        varchar(4) not null,
    genderType      char(1) not null,
    shopType        char(1) not null,
    texts           varchar(80),
    contentUrl      varchar(100) not null,
    title           varchar(50),
    price           int(8),
    term            varchar(15)   
);

create table tblImageHash
(
    pageType    VARCHAR(4) not null,
    genderType  char(1) not null,
    shopType    char(1) not null,
    imgUrl      varchar(256) not null,
    imgId       varchar(64) not NULL
);

ALTER TABLE tblproduct
add constraint tblProduct_fk primary key (pageType);
ALTER TABLE tblImageHash
add constraint tblImageHash_fk primary key (pageType);

alter table spl
add constraint spl_itemcd_fk foreign key (itemcd) references item(itemcd);
alter table bbs
add constraint bbs_wrtnm_fk foreign key (wrtnm) references ctm(ctmid);
alter table bbs 
add constraint bbs_ref_fk foreign key (ref) references bbs(ctxtno);
alter table bbsreply
add constraint bbsreply_ctxtno_fk foreign key (ctxtno) references bbs(ctxtno);
alter table itemorder 
add constraint itemorder_itemcd_fk foreign key (itemcd) references item(itemcd);
alter table itemorder
add constraint itemorder_ctmid_fk foreign key (ctmid) references ctm(ctmid);
alter table tradeinfo
add constraint tradeinfo_itemcd_fk foreign key(itemcd) references item(itemcd);
alter table warehouse
add constraint warehouse_itemcd_fk foreign key (itemcd) references item(itemcd);
alter table item
add constraint item_splno_fk foreign key (splno) references spl(splno);
alter table tblImageHash
add constraint tblImageHash_pageTypes_fk foreign key(pageType) references tblProduct(pageType);
