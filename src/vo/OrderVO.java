package vo;

import org.json.simple.JSONObject;

public class OrderVO {
	private String orderNo;
	private String userId;
	private String productName;
	private int orderAmount;
	private int price;
	private int productId;
	private String link;
	private JSONObject raw;
	
	public JSONObject getRaw() {
		return raw;
	}

	public void setRaw(JSONObject raw) {
		this.raw = raw;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getOrderAmount() {
		return orderAmount;
	}
	
	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}
	
}
