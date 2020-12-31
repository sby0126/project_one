package command;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import service.DetailInputService;
import vo.CartNPayVO;
import vo.ProductVO;

public class DetailInputCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		result.start(request, response);
				
		
		String title = request.getParameter("title");
		int price = Integer.parseInt(request.getParameter("price"));
		
		DetailInputService detailInputService = new DetailInputService();
		
		String ctmid = request.getParameter("id");
		
		List<String> pdOptionList = Arrays.asList(request.getParameterValues("pdoption"));
		
		if(pdOptionList == null) {System.out.println("상품 옵션 데이터가 없습니다");}
		
		System.out.println(pdOptionList);
		
		
		List<CartNPayVO> cartList = new ArrayList<CartNPayVO>();
		
		List<ProductVO> pdlist = detailInputService.getProduct(request, title, price);
		
		ProductVO pd = (ProductVO)pdlist;
		

		for(int i = 0; i < pdOptionList.size();) {
			
			CartNPayVO cartVO = new CartNPayVO();
							
			String size = pdOptionList.get(i);
			String qty = pdOptionList.get(i + 1);
			
			cartVO.setAmount(Integer.parseInt(qty));
			cartVO.setPrice(price);
			cartVO.setCtmId(ctmid);
			cartVO.setLink(pd.getLink());
			cartVO.setContentUrl(pd.getContenturl());	
			

			cartVO.setTitle(title + size);
			
			
			cartList.add(cartVO);
			
			i+=2; 
			
		}
		
		boolean isSuccess = detailInputService.inputDetail(request, cartList);
		
		boolean inputSuccess = false;
		
		String uri = request.getContextPath();
		
		
		
		if(isSuccess) {
			
			System.out.println("상품 추가 성공");
			inputSuccess = true;
			
		} else {
			
			System.out.println("상품을 추가하지 못했습니다");
			
		}
		
		response.setCharacterEncoding("UTF-8");
		
		if(inputSuccess) {
			if(uri.equals("/cart.do")) {
				result.forward(request.getContextPath() + "/pages/cart.jsp");
				
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
