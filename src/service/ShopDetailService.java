package service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dao.ContentDAO;
import vo.ProductVO;

public class ShopDetailService {
	private int id;
	private String pageType;
	private String shopName;
	private ContentDAO contentDAO;
	
	private String thumbNailImage;
	private String mainUrl;
	private String link; 
	
	private List<ProductVO> list;
	private List<ProductVO> thumb;
	
	public ShopDetailService(int id, String pageType) {
		
		// Getting a instance from ContentDAO class.
		contentDAO = ContentDAO.getInstance();
		
		// initialize member variables
		this.id = id;
		this.pageType = pageType;
				
		// ID 값으로 상점을 찾습니다.
		shopName = contentDAO.findShopName(id);
		list = createList();
		
		// 모델 얼굴 썸네일 이미지
		thumb = createThumb();
		
		createThumbnailImage();
		createLink();
	}
	
	public List<ProductVO> createList() {
		List<ProductVO> myList = contentDAO.searchAsShopName(pageType, shopName);
		
		return myList;
	}
	
	public List<ProductVO> createThumb() {
		List<ProductVO> myThumb = contentDAO.findThumbnail(shopName);
		
		return myThumb;
	}
	
	public String getTexts() {
		String ret = "";
		if(thumb != null && !thumb.isEmpty()) {
			ret = thumb.get(0).getTexts();
		}
		
		return ret;
		
	}
	
	public void createThumbnailImage() {
		thumbNailImage = null;
		
		if(!thumb.isEmpty()) {
			ProductVO thumbNailVO = thumb.get(0);
			thumbNailImage = "/images/shop/" + thumbNailVO.getGendertype() + "/" +  thumbNailVO.getShoptype() + "/" + thumbNailVO.getContenturl();
		}
	}
	
	public void createLink() {
		mainUrl = "https://drive.google.com/uc?export=view&id=";
		link = "#";
		
		if(list != null && !list.isEmpty()) {
			link = list.get(0).getLink();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public ContentDAO getContentDAO() {
		return contentDAO;
	}

	public void setContentDAO(ContentDAO contentDAO) {
		this.contentDAO = contentDAO;
	}

	public String getThumbNailImage() {
		return thumbNailImage;
	}

	public void setThumbNailImage(String thumbNailImage) {
		this.thumbNailImage = thumbNailImage;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<ProductVO> getList() {
		return list;
	}

	public void setList(List<ProductVO> list) {
		this.list = list;
	}

	public List<ProductVO> getThumb() {
		return thumb;
	}

	public void setThumb(List<ProductVO> thumb) {
		this.thumb = thumb;
	}
	
	public JSONObject toJSONObject() {
		List<ProductVO> list = getList();
		
		JSONObject root = new JSONObject();
		
		
		
		root.put("pageType", pageType );
		root.put("genderType", "M" );
		root.put("shopType", "S" );
		root.put("offset", new JSONObject());		
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
			
			// updateMyShop(newContentData, vo.getId(), customerId);
			newContentData.put("active", false);
			
			contentData.add(newContentData);
			imageData.put(vo.getContenturl(), vo.getImgid());
		}
		
		return root;
	}
	
}
