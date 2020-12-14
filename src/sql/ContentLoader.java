package sql;

import java.util.HashMap;

public class ContentLoader {
	private HashMap<String, String> qlList;
	
	public ContentLoader() {
		qlList = new HashMap<String, String>(); 
		
		initWithSQL();
	}
	
	public void initWithSQL() {
		
		qlList.put("전체 데이터 추출", "SELECT DISTINCT b.pageType, b.genderType, b.shopType, b.shopName, b.contentUrl, a.imgId, b.*"
						+ " FROM tblImageHash a, tblproduct b"
						+ " WHERE a.pageType = ? AND"
						+ " a.genderType = ? AND"
						+ " a.shopType = ? AND"
						+ " a.imgUrl = b.contentUrl GROUP BY b.contentUrl");
		
		qlList.put("나이 및 카테고리로 필터링", qlList.get("전체 데이터 추출")
					+ " AND texts LIKE ? AND"
					+ " texts LIKE ?"
				);
		
		qlList.put("나이 또는 카테고리로 필터링", qlList.get("전체 데이터 추출")
				+ " AND texts LIKE ?"
			);	
		
		qlList.put("전체 데이터 번호 붙여 추출", "SELECT * FROM (SELECT DISTINCT ROW_NUMBER() OVER w AS 'row_number', b.pageType, b.genderType, b.shopType, b.shopName, b.contentUrl, a.imgId, b.texts, b.title, b.price, b.term"
					+ " FROM tblImageHash a, tblproduct b"
					+ " WHERE a.pageType = ? AND"
					+ " a.genderType = ? AND"
					+ " a.shopType = ? AND"
					+ " a.imgUrl = b.contentUrl"
					+ " WINDOW w AS (order BY id ASC)) AS mytbl"
				);
		
		qlList.put("특정 범위의 데이터만 추출", qlList.get("전체 데이터 번호 붙여 추출") 
					+ " WHERE mytbl.row_number BETWEEN ? AND ?"
				);
		
		qlList.put("번호 붙여 검색", qlList.get("전체 데이터 번호 붙여 추출")
				+ " AND mytbl.texts LIKE ?");
		
		qlList.put("아이템 카테고리 생성", "SELECT trim(t.category) as category, count(t.category)"
					+ " FROM (select REGEXP_REPLACE(texts, '[\\\\d]+(?:대)[,]*', '') AS category, pageType from tblproduct) AS t"
					+ " WHERE t.pageType='item'"
					+ " GROUP BY t.category"
					+ " HAVING count(t.category) >= 1"
					+ " ORDER BY COUNT(t.category) DESC"
				);
		
	}
	
	public String get(String command) {
		return qlList.get(command);
	}
}
