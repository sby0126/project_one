package command;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import action.ActionResult;
import service.DetailInputService;

public class DetailInputCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result.start(request, response);
				
		
		String title = request.getParameter("title");
		int price = (Integer.parseInt(request.getParameter("price")) / Integer.parseInt(request.getParameter("amount")));
		int productId = Integer.parseInt(request.getParameter("productId"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		
		String uri = null;
		
		DetailInputService detailInputService = new DetailInputService();
		
		boolean isSuccess = detailInputService.inputDetail(request, title, price, amount);
		boolean inputSuccess = false;
		
		uri = request.getPathInfo();
		
		int choice;
		
		if(isSuccess) {
			
			System.out.println("상품 추가 성공");
			inputSuccess = true;
			
		} else {
			
			System.out.println("상품을 추가하지 못했습니다");
			
		}
		
		response.setCharacterEncoding("UTF-8");
		
		if(inputSuccess) {
			if(uri.equals("/cart.do")) {
				
				choice = JOptionPane.showConfirmDialog(null, 
						"상품이 장바구니에 담겼습니다 \n" 
			 			 + "장바구니로 이동 하시겠습니까?", null, 1);					

				// 출처: https://unikys.tistory.com/215 [All-round programmer]
				
				switch(choice) {
					case 0 : result.forward(request.getContextPath() + "/pages/cart.jsp");
							 request.setAttribute("title", title);
							 request.setAttribute("perPrice", price);
							 request.setAttribute("amount", amount);
							 break;
							 
					case 1 : result = null;
							 break;
				}
				
			}
			
			if(uri.equals("/pay.do")) {
				result.forward(request.getContextPath() + "/pages/payments.jsp");
				
			}
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("");
			out.println("history.go(-1);");
			out.println("</script>");
			result = null;
		}
		
		return result;
	}
}
