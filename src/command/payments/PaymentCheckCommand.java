package command.payments;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.ActionResult;
import command.Command;
import service.payments.PaymentService;

public class PaymentCheckCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
				
		try {
			String imp_uid = request.getParameter("imp_uid");
			String merchant_uid = request.getParameter("merchant_uid");
			String productName = request.getParameter("productName");
			String productId = request.getParameter("productId");
			int paid_amount = Integer.parseInt(request.getParameter("paid_amount"));
			
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("id");
			PaymentService service = new PaymentService();
			
			String access_token = service.getAccessToken();
			
			boolean isSuccess = service.orderKakaoPay(imp_uid, id, productId, paid_amount, access_token);
			
			if(isSuccess) {
				// 검증 완료... 구매 완료 처리.
			} else {
				// 검증 실패... 뭔가 이상하다.
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		
		return result;
	}
}
