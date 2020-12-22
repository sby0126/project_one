package service;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import vo.ProductVO;

public class DetailInputService {

	public boolean inputDetail(String title, String price, int qty) {
		
		ResultSet rs = null;
		List<ProductVO> list = null;
		boolean success = false;
		ItemService itemService = new ItemService();

		HttpServletRequest request = null;
		
		list = itemService.getDAO().getDetail(title, price);
		
		request.setAttribute("list", list);
		
		System.out.println(list);
		
		success = itemService.getDAO().insertDetail(list, qty);
		
		return success;
	}
	
}
