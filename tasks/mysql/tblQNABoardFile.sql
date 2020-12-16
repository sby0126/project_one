DROP TABLE if EXISTS tblQNABoardFile;
CREATE TABLE tblQNABoardFile (
	parentArticleID int(11) NOT NULL,
	filename TEXT,
	mimetype VARCHAR(15) NOT NULL,
	
	CONSTRAINT FOREIGN KEY tblQNABoardImages_fk (parentArticleID) REFERENCES tblQNABoard(articleID)
);

INSERT INTO tblQNABoardFile VALUES(4, 'myfile.png', 'image/png');
TRUNCATE tblQNABoardFile;

-- text/html
-- image/png
-- text/css
