DROP PROCEDURE if EXISTS selectContent();

delimiter $$
CREATE PROCEDURE selectContent(
	IN uPageType VARCHAR(4),
	IN ugenderType VARCHAR(1),
	IN uShopType VARCHAR(1),
	IN category VARCHAR(10),
	IN ages VARCHAR(5),
	IN uStart INT,
	IN uEnd INT
)
BEGIN

	SELECT DISTINCT * 
	FROM tblproduct
	WHERE pageType = uPageType AND
	genderType = ugenderType AND
	shopType = uShopType
	AND texts LIKE category
	AND texts LIKE ages
	group by contentUrl
	ORDER BY id
	limit uStart, uEnd;
	
END $$
delimiter ;s

CALL selectContent('shop', 'M', 'S', '%트랜드%', '%10대%', 0, 100);