SET FOREIGN_KEY_CHECKS = 0;
drop TABLE if exists tblQNABoard;
SET FOREIGN_KEY_CHECKS = 1;

create table tblQNABoard(
    articleID int(11) not null auto_increment,
    authorID varchar(15) ,
    articleType VARCHAR(20),
    title varchar(500),
    content text,
    pos smallint(7) UNSIGNED DEFAULT 0,
    parentID smallint(7) DEFAULT 0,
    depth smallint(7) UNSIGNED DEFAULT 0,
    regdate TIMESTAMP,
    recommandCount smallint(7) unsigned default 0,
    viewCount smallint(7) UNSIGNED DEFAULT 0,
    imageFileName varchar(255),
    filesize int(11) DEFAULT 0,
    
    CONSTRAINT PRIMARY KEY tblQNABoard_pk (articleID)
);

alter table tblQNABoard AUTO_INCREMENT = 1;
alter table tblQNABoard ADD constraint tblQNABoard_fk FOREIGN key(authorID) REFERENCES tblCustomer(ctmid) 
	ON DELETE CASCADE 
	ON UPDATE CASCADE;

insert into tblQNABoard(authorID, articleType, title, content, regdate, imageFileName) VALUES('admin', 'Normal', '안녕하세요', '내용입니다', NOW(), NULL);
insert into tblQNABoard(authorID, articleType, title, content, regdate, imageFileName) VALUES('admin', 'Normal', '안녕하세요', '내용입니다', NOW(), NULL);
insert into tblQNABoard(authorID, articleType, title, content, regdate, imageFileName) VALUES('admin', 'Normal', '안녕하세요', '내용입니다', NOW(), NULL);

UPDATE tblqnaboard SET title = '1', content='2', regdate = NOW() WHERE articleID = 1;

-- 

DROP TABLE if EXISTS tblQNABoardComments;
CREATE TABLE tblQNABoardComments (
	commentID INT(11) NOT NULL auto_increment, 
	parent_articleID INT(11) NOT NULL,
	authorID varchar(15) NOT null,
	content TEXT,
	regdate TIMESTAMP,
   pos smallint(7) UNSIGNED DEFAULT 0,
   parentID smallint(7) DEFAULT 0,
   depth smallint(7) UNSIGNED DEFAULT 0,
   
   CONSTRAINT PRIMARY KEY commentID_pk (commentID)
);

alter table tblQNABoardComments ADD constraint tblQNABoardComments_fk FOREIGN key(parent_articleID) REFERENCES tblQNABoard(articleID);
ALTER TABLE tblQNABoardComments ADD constraint tblQNABoardComments_authorID_fk FOREIGN KEY(authorID) REFERENCES tblCustomer(ctmid);

INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate) VALUES(1, 'admin', 'wow....', NOW());
INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate) VALUES(1, 'admin', '테스트 댓글', NOW());
INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate) VALUES(2, 'admin', '테스트 댓글', NOW());

	
INSERT INTO tblQNABoardComments(parent_articleID, authorID, content, regdate, pos, parentID, depth) 
	VALUES(1, 'admin', 'test.....', NOW(), 1, 1, 1);


UPDATE tblQNABoardComments SET content = '테스트2' WHERE commentID = 1 AND authorID = 'admin';
update tblQNABoard set viewCount = viewCount + 1 where articleID = 1;

select authorID, content, regdate, pos, parentID, depth 
	from tblQNABoardComments 
	where parent_articleID = 1 order by parentID desc, pos, commentID;

SELECT INSERT(content, LENGTH(content), 1, ',') FROM tblqnaboardcomments;
SELECT FROM_BASE64(TO_BASE64("WOWzzzzzzzzzzzzzzzzz"));

-- UPDATE tblqnaboard SET content = (SELECT concat(content, ',', '새로룬내용') FROM tblqnaboard WHERE articleID = 33) WHERE articleID = 33;
UPDATE tblqnaboard SET imageFileName = concat(ifnull(imageFileName, ''), ',', '새로운내용') WHERE articleID = 33;
SELECT imageFileName FROM tblqnaboard WHERE articleID = 33;
SELECT REGEXP_SUBSTR(content, '<img src=(.*)>', 1, 1, 'mi') FROM tblqnaboard WHERE articleID = 33;

tblproduct

ㅜㅜ