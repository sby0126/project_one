DROP TRIGGER if EXISTS insertProductTrg;
DELIMITER $$
CREATE TRIGGER insertProductTrg
	AFTER INSERT
	ON tblproduct
	FOR EACH row
BEGIN 
	DECLARE productId int(11);
	DECLARE productName VARCHAR(50);
	DECLARE productPrice VARCHAR(10);
	DECLARE uPrice INT(10) DEFAULT 0;
	DECLARE productName2 VARCHAR(100);
	
	if NEW.pageType = 'item' then
	
		SET productId = NEW.id;
		SET productName = NEW.title;
		SET uPrice = convert(REPLACE(ifnull(NEW.price, '0'), ',',''), SIGNED);
	
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
	END if;
END $$
DELIMITER ;