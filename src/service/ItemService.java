package service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import vo.ProductVO;

public class ItemService extends ContentService {
	
	public JSONObject getOffset() {
		JSONObject offset = new JSONObject();
		
		offset.put("start", 0);
		offset.put("count", 68);
		
		return offset;
	}
	
	/**
	 * JSON을 생성합니다.
	 *  
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getItem(String pageType, String genderType, String shopType, String category, String ages) {	
		List<ProductVO> list = getDAO().getData(pageType, genderType, shopType, category, ages);
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
			
			contentData.add(newContentData);
			imageData.put(vo.getContenturl(), vo.getImgid());
		}
		
		return root;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject searchAsShop(String pageType, String shopName) {
		List<ProductVO> list = getDAO().searchAsShopName(pageType, shopName);
		JSONObject root = new JSONObject();
		
		root.put("pageType", pageType );
		root.put("genderType", "M" );
		root.put("shopType", "S" );
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
			
			contentData.add(newContentData);
			imageData.put(vo.getContenturl(), vo.getImgid());
		}
		
		return root;
		
	}
}
