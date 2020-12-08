package service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import vo.ProductVO;

public class ShopService extends ContentService {
	
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
	public JSONObject getShop(String pageType, String genderType, String shopType) {	
		List<ProductVO> list = getDAO().getData(pageType, genderType, shopType);
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
			newContentData.put("shopName", vo.getShopname());
			newContentData.put("texts", vo.getTexts());
			newContentData.put("url", vo.getContenturl());
			
			contentData.add(newContentData);
			imageData.put(vo.getContenturl(), vo.getImgid());
		}
		
		return root;
	}
}
