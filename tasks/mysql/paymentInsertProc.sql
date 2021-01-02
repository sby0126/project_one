
DELIMITER $$
DROP PROCEDURE IF EXISTS paymentInsertProc;
CREATE PROCEDURE paymentInsertProc()
BEGIN
	DECLARE productId int(11);
	DECLARE productName VARCHAR(50);
	DECLARE productPrice VARCHAR(10);
	DECLARE uPrice INT(10) DEFAULT 0;
	DECLARE productName2 VARCHAR(100);
	
	DECLARE endOfRow BOOLEAN DEFAULT FALSE;
	
	DECLARE productCursor CURSOR FOR
		SELECT id, title, price FROM tblproduct;
		
	-- is it the end of row?
	DECLARE CONTINUE HANDLER
		FOR NOT FOUND SET endOfRow = TRUE;
	
	OPEN productCursor;
	
	cursor_loop: LOOP
		FETCH productCursor INTO productId, productName, productPrice;
		
		if endOfRow then 
			leave cursor_loop;
		END if;
		
		SET uPrice = convert(REPLACE(ifnull(productPrice, '0'), ',',''), SIGNED);
		
		-- add BM
		SET productName2 = CONCAT(productName, '-', 'BM');
		INSERT INTO tblExtItem(id, title, price, stock, regdate) VALUES(productId, productName2, uPrice, 99, NOW());
		
		-- add BL
		SET productName2 = CONCAT(productName, '-', 'BL');		
		INSERT INTO tblExtItem(id, title, price, stock, regdate) VALUES(productId, productName2, uPrice, 99, NOW());
		
		-- add WM
		SET productName2 = CONCAT(productName, '-', 'WM');		
		INSERT INTO tblExtItem(id, title, price, stock, regdate) VALUES(productId, productName2, uPrice, 99, NOW());
		
		-- add WL
		SET productName2 = CONCAT(productName, '-', 'WL');		
		INSERT INTO tblExtItem(id, title, price, stock, regdate) VALUES(productId, productName2, uPrice, 99, NOW());		
		
	END LOOP cursor_loop;
	
	CLOSE productCursor;
	
END $$
DELIMITER ;

CALL paymentInsertProc();