CREATE DATABASE if NOT EXISTS ONE;
USE ONE;

DROP TABLE if EXISTS tblCustomer;

CREATE TABLE tblCustomer (
    CTMID            VARCHAR(15) NOT NULL,         
    CTMPW            VARCHAR(64) NOT NULL,         
    CTMNO            INT AUTO_INCREMENT UNIQUE NOT NULL, 
    CTMNM            VARCHAR(15) NOT NULL,         
    ADDR             VARCHAR(100),                  
    TEL              VARCHAR(15),                  
    EMAIL            VARCHAR(30) NOT NULL ,         
    ZIPCODE          CHAR(5),                      
    IS_ADMIN         CHAR(1) DEFAULT 'N',           
    JOINDATE         DATE NOT NULL,                
    SALT             VARCHAR(16) NOT NULL,         
    LAST_LOGIN         INT,                 
    FAILED_LOGIN_COUNT TINYINT,                   
    IS_LOCK 			CHAR(1) DEFAULT 'N',
	 
	 
	 CONSTRAINT PRIMARY KEY TBLCUSTOMER_CTMID_PK (CTMID),
	 CONSTRAINT UNIQUE TBLCUSTOMER_EMAIL_UK (EMAIL)                  
);

alter table tblCustomer AUTO_INCREMENT = 1;

INSERT INTO tblCustomer (CTMID, CTMPW, CTMNO, CTMNM, EMAIL, IS_ADMIN, JOINDATE, SALT) 
    VALUES('admin', 'admin', null, '관리자', 'admin@projectone.co.kr', 'Y', CURDATE(), 'AAAABBBBCCCCDDDD');