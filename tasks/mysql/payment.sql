
DROP PROCEDURE if exists payment;

delimiter $$
CREATE PROCEDURE payment(
	IN imp_uid VARCHAR(100),
	IN buyer_name VARCHAR(15),
	IN buyer_email VARCHAR(30),
	IN buyer_tel VARCHAR(20),
	IN buyer_addr VARCHAR(150),
	IN buyer_postcode VARCHAR(10),
	IN product_id INT(11),
	IN product_name VARCHAR(250),
	IN paid_amount INT(8),
	IN merchant_uid VARCHAR(100),
	IN payment_status VARCHAR(10)
)
BEGIN
	START TRANSACTION;
	SAVEPOINT sp;
	INSERT INTO tblPayment VALUES(imp_uid, buyer_name, buyer_email, buyer_tel, buyer_addr, buyer_postcode, product_id, product_name, paid_amount, merchant_uid, payment_status, NOW());
END $$
delimiter ;