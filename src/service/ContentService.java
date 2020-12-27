package service;

import org.json.simple.JSONObject;

import dao.ContentDAO;

public class ContentService {
	private ContentDAO contentDAO;
	
	public ContentService() {
		contentDAO = ContentDAO.getInstance();
	}
	
	public ContentDAO getDAO() {
		return contentDAO;
	}
	
	@SuppressWarnings("unchecked")
	public void updateMyShop(JSONObject newContentData, int shopId, String customerId) {
		
		// 마이샵 버튼이 활성화 되어있어야 하는 지 여부
		if(customerId != null) {
			MyShopService myShopService = new MyShopService();
			boolean isValidMyShop = myShopService.isValidMyShop(shopId, customerId);
			newContentData.put("active", isValidMyShop);	
		} else {
			newContentData.put("active", false);
		}
	}
	
}
