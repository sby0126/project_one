-- make category
SELECT t.category, count(t.category)
FROM (select REGEXP_REPLACE(texts, '[\\d]+(?:대)[,]*', '') AS category, pageType from tblproduct) AS t
WHERE t.pageType='item'
GROUP BY t.category
HAVING count(t.category) >= 1
ORDER BY COUNT(t.category) DESC;