package command.payments;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import action.ActionResult;
import command.Command;
import service.payments.PaymentService;

public class PaymentCheckCommand extends Command {
	@SuppressWarnings("unchecked")
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		// 응답 JSON 파일을 생성합니다.
		JSONObject responseData = new JSONObject();		
				
		try {
			// 구매 정보를 가져옵니다.
			String imp_uid = request.getParameter("imp_uid");
			String merchant_uid = request.getParameter("merchant_uid");
			String productName = request.getParameter("product_name");
			String productId = request.getParameter("product_id");
			int paid_amount = Integer.parseInt(request.getParameter("paid_amount"));
			
			// 세션에서 아이디 값을 가져옵니다.
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("id");
			PaymentService service = new PaymentService();
			
			// 액세스 토큰을 생성합니다.
			String access_token = service.getAccessToken();
			System.out.println(access_token + "(액세스 토큰)을 획득하였습니다.");
			
			// 획득한 액세스 토큰으로 카카오 페이 결제를 시도합니다.
			boolean isSuccess = service.orderKakaoPay(imp_uid, id, productId, paid_amount, access_token);

			if(isSuccess) {
				// 검증 완료... 구매 완료 처리.
				responseData.put("status", "success");
				responseData.put("message", "결제에 성공하였습니다.");
			} else {
				// 검증 실패... 뭔가 이상하다.
				responseData.put("status", "forgery");
				responseData.put("message", "위조된 결제 시도입니다.");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(responseData.toJSONString());
		
		
		return null;
	}
}