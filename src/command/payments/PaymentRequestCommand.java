package command.payments;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;
import service.payments.GetCustomerService;

public class PaymentRequestCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
				
		String id = (String)request.getSession().getAttribute("id");
		
		GetCustomerService customerService = new GetCustomerService();
		HashMap<String, String> customerInfo = customerService.getCustomer(id);
		
		request.setAttribute("name", customerInfo.get("name"));
		request.setAttribute("email", customerInfo.get("email"));
		request.setAttribute("phone", customerInfo.get("phone"));
		request.setAttribute("address", customerInfo.get("address"));
		request.setAttribute("zipcode", customerInfo.get("zipcode"));
		request.setAttribute("productName", request.getParameter("productName"));
		request.setAttribute("productId", request.getParameter("productId"));
		
		result.forward("/pages/payments-form.jsp");
		
		return result;
	}
}
