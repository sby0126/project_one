CREATE DATABASE if NOT EXISTS MYDB;
USE MYDB;

DROP TABLE if EXISTS tblCustomer;

CREATE TABLE tblCustomer (
    CTMID            VARCHAR(15) NOT NULL,         
    CTMPW            VARCHAR(64) NOT NULL,         
    CTMNO            INT(8) AUTO_INCREMENT UNIQUE NOT NULL, 
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

TRUNCATE tblCustomer;

INSERT INTO tblCustomer (CTMID, CTMPW, CTMNO, CTMNM, EMAIL, IS_ADMIN, JOINDATE, SALT) 
    VALUES('admin', '6df48b553264e6fd617d194e5afd76ba8d893cb0f54b1a7d8c6035317874e0b5', null, '관리자', 'admin@projectone.co.kr', 'Y', CURDATE(), '0d6b91ff7ea2bd9d');
