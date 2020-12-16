package service;

import java.sql.ResultSet;
import java.util.List;

import vo.ProductVO;
import dao.ContentDAO;

public class DetailInputService {

	public boolean inputDetail(String title, String price, int qty) {
		
		ResultSet rs = null;
		List<ProductVO> list = null;
		boolean success = false;
		ProductVO p = null;
		ItemService itemService = new ItemService();
		
		
		list = itemService.getDAO().getDetail(title, price);
		
		success = itemService.getDAO().insertDetail(list, qty);
		

		return success;
	}
	
	
	
	
	
}
