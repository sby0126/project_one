package service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import vo.CartNPayVO;
import vo.ProductVO;
import service.ItemService;

public class DetailInputService {

	
	public List<ProductVO> getProduct(HttpServletRequest req, String title, int price) {
		
		List<ProductVO> pdlist = null;
		
		ItemService itemService = new ItemService();
		
		String id = req.getParameter("id");
		
		pdlist = itemService.getDAO().getDetail(title, price);
		
		req.setAttribute("list", pdlist);
		req.setAttribute("id", id);
		
		
		return pdlist;
		
	}
	
	public boolean inputDetail(HttpServletRequest req, List<CartNPayVO> list) {

		boolean success = false;
		ItemService itemService = new ItemService();
		
		String id = req.getParameter("id");
		
		
		System.out.println(list);
		
		success = itemService.getDAO().insertDetail(id, list);
		
		return success;
	}
	
	
	
}
