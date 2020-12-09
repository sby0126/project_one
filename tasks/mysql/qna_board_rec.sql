DROP TABLE if EXISTS tblqnaboardrec;

CREATE TABLE tblQnaBoardRec (
	board_id int(11) NOT NULL,
	receiver_id VARCHAR(15) NOT NULL
);

ALTER TABLE tblQnaBoardRec ADD CONSTRAINT tblQnaBoardRec_PK PRIMARY KEY(board_id, receiver_id);

INSERT INTO tblQnaBoardRec (board_id, receiver_id) VALUES (1, 'admin');
INSERT INTO tblQnaBoardRec (board_id, receiver_id) VALUES (2, 'admin');

SELECT * FROM tblQnaBoardRec;

SELECT COUNT(*) 
FROM tblqnaboardrec
WHERE board_id = 1;

DELETE FROM tblQnaBoardRec WHERE board_id = 1 AND receiver_id = 'admin';
DELETE FROM tblQnaBoardRec WHERE board_id = 2 AND receiver_id = 'admin';