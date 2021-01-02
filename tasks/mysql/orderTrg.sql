
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