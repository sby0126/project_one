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
						+ " a.imgUrl = b.contentUrl");
		
		qlList.put("전체 데이터 추출2", "SELECT DISTINCT *, getItemCount(shopName) AS cnt"
				+ " FROM tblproduct"
				+ " WHERE pageType = ? AND"
				+ " genderType = ? AND"
				+ " shopType = ?"
				);		
		
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
		
		qlList.put("아이템 카테고리 생성", "CALL getItemCategory()");
		
		qlList.put("브랜드 별 검색", "SELECT DISTINCT a.*, b.* FROM tblImageHash a, tblproduct b WHERE b.pageType = ? AND shopName = ? AND imgUrl = contentUrl GROUP BY contentUrl");
		
		qlList.put("브랜드 명 찾기", "select shopName from tblproduct where id = ?");
		
		qlList.put("브랜드 썸네일 찾기", "SELECT * FROM tblproduct WHERE pageType='shop' AND shopName = ? GROUP BY contentUrl");
		
		qlList.put("ID로 상품 찾기", "SELECT DISTINCT b.pageType, b.genderType, b.shopType, b.shopName, b.contentUrl, a.imgId, b.*"
				+ " FROM tblImageHash a, tblproduct b"
				+ " where a.imgUrl = b.contentUrl"
				+ " AND b.id = ?");
		
		qlList.put("상품 찾기", "SELECT *"
				+ " FROM tblproduct"
				+ " WHERE id = ?"
				);
		
		qlList.put("getMyShop", "SELECT * FROM tblMyShop WHERE ctm_id = ?");
		qlList.put("addMyShop", "INSERT INTO tblMyShop(ctm_id, shop_id) values(?, ?)");
		qlList.put("deleteMyShop", "DELETE FROM tblMyShop where ctm_id = ? and shop_id = ?");
		qlList.put("deleteAllMyShop", "DELETE FROM tblMyShop where ctm_id = ?");
		
	}
	
	public String get(String command) {
		return qlList.get(command);
	}
}
