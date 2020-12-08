package sql;

import java.util.HashMap;

public class ContentLoader {
	private HashMap<String, String> qlList;
	
	public ContentLoader() {
		qlList = new HashMap<String, String>(); 
		
		initWithSQL();
	}
	
	public void initWithSQL() {
		qlList.put("search", 
				"SELECT DISTINCT b.pageType, b.genderType, b.shopType, b.shopName, a.imgId"
						+ " FROM tblimagehash a, tblproduct b"
						+ " WHERE a.pageType = ? AND"
						+ " a.genderType = ? AND"
						+ " a.shopType = ? AND"
						+ " a.imgUrl = b.contentUrl");
	}
	
	public String get(String command) {
		return qlList.get(command);
	}
}
