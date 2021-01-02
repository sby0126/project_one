
DROP TRIGGER if EXISTS orderTrg;
delimiter $$
CREATE TRIGGER orderTrg
	AFTER insert
	ON tblOrder
	FOR EACH row
BEGIN
	UPDATE tblExtItem SET stock = stock - NEW.order_amount
		WHERE title = new.product_name;
END $$
delimiter ;

DROP TRIGGER if exists prodTrg;
delimiter $$
CREATE TRIGGER prodTrg
	AFTER update
	ON tblExtItem
	FOR EACH row
BEGIN
	DECLARE orderAmount INT;
	
	-- 구매한 갯수 : (구매 전의 갯수 - 구매 후의 갯수)
	SET orderAmount = OLD.stock - NEW.stock;
	
	INSERT INTO tblDeliver(product_name, ACCOUNT) VALUES(NEW.title, orderAmount);
	
END $$
delimiter ;
