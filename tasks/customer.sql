--*****************************************************************************
-- 고객 테이블
--*****************************************************************************
create table tblCustomer (
    CTMID            VARCHAR2(15) NOT NULL,         -- ID (PK)
    CTMPW            VARCHAR2(64) NOT NULL,         -- 암호화된 비밀번호 (64자리)
    CTMNO            NUMBER(8) NOT NULL,            -- 번호
    CTMNM            VARCHAR(15) NOT NULL,          -- 이름
    ADDR             VARCHAR2(30),                  -- 주소
    TEL              VARCHAR2(15),                  -- 전화번호
    EMAIL            VARCHAR2(30) NOT NULL,         -- 이메일
    ZIPCODE          CHAR(5),                       -- 새로운 우편번호 (5자리)
    IS_ADMIN         CHAR(1) DEFAULT 'N',           -- 관리자 여부 (Y/N)
    JOINDATE         DATE NOT NULL,                 -- 가입일
    SALT             VARCHAR2(16) NOT NULL,         -- 해시 값을 만들기 위한 값
    LAST_LOGIN         NUMBER(13),                  -- 최근 로그인
    FAILED_LOGIN_COUNT NUMBER(2),                   -- 로그인 실패 횟수
    IS_LOCK CHAR(1) DEFAULT 'N'                     -- 휴먼 계정 여부 (Y/N)
);

-- 게시판 테이블과 연동되어야 하므로 기본키를 ID로 설정
ALTER TABLE tblCustomer add CONSTRAINT tblCustomer_CTMID_pk PRIMARY KEY(CTMID);

-- 시퀀스
CREATE SEQUENCE CUSTNO_SEQ 
INCREMENT BY 1
START WITH 1
MINVALUE 1
NOCYCLE;

INSERT INTO tblCustomer (CTMID, CTMPW, CTMNO, CTMNM, EMAIL, IS_ADMIN, JOINDATE, SALT) 
    VALUES('admin', 'admin', CUSTNO_SEQ.NEXTVAL, '관리자', 'admin@projectone.co.kr', 'Y', sysdate, 'AAAABBBBCCCCDDDD');