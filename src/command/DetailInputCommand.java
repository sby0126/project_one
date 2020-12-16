package command;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import service.DetailInputService;

public class DetailInputCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result.start(request, response);
				
		
		String title = request.getParameter("title");
		String price = request.getParameter("price");
		int qty = Integer.parseInt(request.getParameter("qty"));
		
		
		DetailInputService detailInputService = new DetailInputService();
		
		boolean isSuccess = detailInputService.inputDetail(title, price, qty);
		
		if(isSuccess) {
			
			System.out.println("상품 추가 성공");
			
		} else {
			
			System.out.println("상품을 추가하지 못했습니다");
			
		}
		
		response.setCharacterEncoding("UTF-8");
		
		return null;
	}
}
