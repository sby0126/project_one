package service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import vo.InterestVO;
import vo.ProductVO;

public class SaleService extends ContentService {
	
	public JSONObject getOffset() {
		JSONObject offset = new JSONObject();
		
		offset.put("start", 0);
		offset.put("count", 68);
		
		return offset;
	}
	
	@Override
	public void updateMyShop(JSONObject newContentData, int shopId, String customerId) {
		InterestService service = new InterestService();
		List<InterestVO> list = service.getInterest(customerId);
		
		// 관심 상품이 있는지 찾습니다.
		boolean isValid = list.stream().filter(i -> {
			return i.getProductId() == shopId;
		}).findAny().isPresent();
		
		if(isValid) {
			newContentData.put("active", true);
		} else {
			newContentData.put("active", false);
		}
	}
	
	/**
	 * JSON을 생성합니다.
	 *  
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getSale(String pageType, String genderType, String shopType, String customerId, int start, int end) {	
		List<ProductVO> list = getDAO().getData(pageType, genderType, shopType, null, null, start, end);
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
			newContentData.put("shop", vo.getShopname());
			newContentData.put("term", vo.getTerm());
			newContentData.put("url", vo.getContenturl());
			newContentData.put("link", vo.getLink());
			newContentData.put("id", vo.getId());
			
			if(customerId != null) {
				MyShopService service = new MyShopService();
				List<Integer> iList = service.getIdList(customerId);
				
				if(iList.contains(vo.getId())) {
					newContentData.put("isMyShop", true);
				} else {
					newContentData.put("isMyShop", false);
				}
				
				newContentData.put("id", vo.getId());	
			} else {
				newContentData.put("isMyShop", false);
			}
			
			updateMyShop(newContentData, vo.getId(), customerId);
			
			contentData.add(newContentData);
//			imageData.put(vo.getContenturl(), vo.getImgid());
		}
		
		return root;
	}
}
