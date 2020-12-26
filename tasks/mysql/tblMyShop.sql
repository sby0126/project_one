
DROP TABLE if EXISTS tblMyShop;

create table tblMyShop (
	ctm_id VARCHAR(15) NOT NULL,
	shop_id int(11) NOT NULL
);

ALTER TABLE tblMyShop add FOREIGN KEY(ctm_id) REFERENCES tblCustomer(ctmid);
ALTER TABLE tblMyShop ADD FOREIGN KEY(shop_id) REFERENCES tblproduct(id);