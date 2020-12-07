package sql;

import java.util.HashMap;

public class ShopSQL {
	private HashMap<String, String> qlList;
	
	public ShopSQL() {
		qlList = new HashMap<String, String>(); 
	}
	
	public void initWithSQL() {
//		"SELECT DISTINCT b.pageType, b.genderType, b.shopType, b.shopName, a.imgId",
//		"FROM tblimagehash a, tblproduct b",
//		"WHERE a.pageType = ? AND",
//		"a.genderType = ? AND",
//		"a.shopType = ? AND" ,
//		"a.imgUrl = b.contentUrl"
	}
}
