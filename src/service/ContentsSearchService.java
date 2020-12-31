package service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.ContentDAO;
import vo.ProductVO;

public class ContentsSearchService extends ContentService  {
	
	public boolean searchKeyword(String keyword, String time) {
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		
		boolean isOK = contentDAO.insertKeywordToBestList(keyword, time);
		
		return isOK;
	}
	
	public JSONObject getOffset() {
		JSONObject offset = new JSONObject();
		
		offset.put("start", 0);
		offset.put("count", 68);
		
		return offset;
	}
	
	
	@SuppressWarnings("unchecked")
	public JSONObject getItem(String pageType, String genderType, String shopType, String category, String ages, String keyword, String customerId, int start, int end) {	
		List<ProductVO> list = getDAO().searchData(pageType, genderType, shopType, category, ages, keyword, start, end);
		JSONObject root = new JSONObject();
		
		root.put("pageType", pageType );
		root.put("genderType", genderType );
		root.put("shopType", shopType );
		root.put("offset", getOffset());		
		root.put("imageUrl", "https://drive.google.com/uc?export=view&id=");
		
		JSONArray contentData = new JSONArray();
		JSONObject imageData = new JSONObject();
		
		root.put("contentData", contentData);
		root.put("imageData", imageData);
		
		for(ProductVO vo : list) {
			JSONObject newContentData = new JSONObject();
			
			newContentData.put("category", vo.getShoptype());
			newContentData.put("title", vo.getTitle());
			newContentData.put("price", vo.getPrice());
			newContentData.put("shop", vo.getShopname());
			newContentData.put("url", vo.getContenturl());
			newContentData.put("link", vo.getLink());
			newContentData.put("id", vo.getId());
						
			contentData.add(newContentData);
			imageData.put(vo.getContenturl(), vo.getImgid());
		}
		
		return root;
	}
	
}
