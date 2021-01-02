package vo;

public class OrderVO {
	private String orderNo;
	private String userId;
	private String productName;
	private int orderAmount;
	
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
