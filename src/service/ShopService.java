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
	public JSONObject getShop(String pageType, String genderType, String shopType, String category, String ages, String customerId, int start, int end) {
		List<ProductVO> list = null;
		
		list = getDAO().getData(pageType, genderType, shopType, category, ages, start, end);
		
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
			newContentData.put("id", vo.getId());
			newContentData.put("link", vo.getLink());
			
			updateMyShop(newContentData, vo.getId(), customerId);
			
			contentData.add(newContentData);
			// imageData.put(vo.getContenturl(), vo.getImgid());
		}
		
		return root;
	}
	
	/**
	 * 특정 쇼핑몰의 전체 상품을 검색하여 JSON으로 반환합니다.
	 * 
	 * @param pageType 페이지 타입을 기입하십시오.
	 * @param id 유일키를 입력해주십시오.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getItemForCertainShop(String pageType, int id) {

		String shopName = getDAO().findShopName(id);
		
		System.out.println("shopName : " + shopName);
		
		List<ProductVO> list = getDAO().searchAsShopName(pageType, shopName);
		
		if(list == null) {
			return null;
		}
		
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
			newContentData.put("shopName", vo.getShopname());
			newContentData.put("texts", vo.getTexts());
			newContentData.put("url", vo.getContenturl());
			newContentData.put("id", vo.getId());
			newContentData.put("link", vo.getLink());
			
			contentData.add(newContentData);
			// imageData.put(vo.getContenturl(), vo.getImgid());
		}
		
		return root;
		
	}
}
