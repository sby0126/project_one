SELECT DISTINCT b.pageType, b.genderType, b.shopType, b.shopName, b.contentUrl, a.imgId, b.*
FROM tblimagehash a, tblproduct b
WHERE a.pageType = 'shop' AND
a.genderType = 'M' AND
a.shopType = 'S' AND
a.imgUrl = b.contentUrl AND
texts LIKE '%빈티지%' AND
TEXTs LIKE '%10대%';

SELECT * FROM (SELECT DISTINCT ROW_NUMBER() OVER w AS 'row_number', b.pageType, b.genderType, b.shopType, b.shopName, b.contentUrl, a.imgId, b.texts, b.title, b.price, b.term
FROM tblimagehash a, tblproduct b
WHERE a.pageType = 'shop' AND
a.genderType = 'M' AND
a.shopType = 'S' AND
a.imgUrl = b.contentUrl
WINDOW w AS (order BY id ASC)) AS mytbl
WHERE mytbl.row_number BETWEEN 0 AND 205
AND mytbl.texts LIKE '%빅사이즈%';