package command.payments;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import dao.ContentDAO;
import service.payments.GetCustomerService;
import vo.ProductVO;

/**
 * 결제 처리를 요청합니다.
 */
public class PaymentRequestCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
				
		String id = (String)request.getSession().getAttribute("id");
		int productId = Integer.parseInt(request.getParameter("productId"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		
		GetCustomerService customerService = new GetCustomerService();
		HashMap<String, String> customerInfo = customerService.getCustomer(id);
		
		// 다음 정보로 결제 페이지를 요청합니다.
		request.setAttribute("name", customerInfo.get("name"));
		request.setAttribute("email", customerInfo.get("email"));
		request.setAttribute("phone", customerInfo.get("phone"));
		request.setAttribute("address", customerInfo.get("address"));
		request.setAttribute("zipcode", customerInfo.get("zipcode"));
		request.setAttribute("productName", request.getParameter("productName"));
		request.setAttribute("productId", productId);
		request.setAttribute("amount", amount);
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		ProductVO vo = contentDAO.findShopDataAsID(productId);

		int price = Integer.parseInt(vo.getPrice().replaceAll("[\\,]+", ""));
		
		request.setAttribute("price", price * amount);
		
		result.forward("/pages/payments-form.jsp");
		
		return result;
	}
}
