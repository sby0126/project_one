package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import action.ActionResult;
import service.InterestService;

/**
 * 관심 상품을 삭제합니다.
 */
public class DeleteInterestCommand extends Command {
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		// 세션에 있는 로그인된 아이디 값을 가져옵니다.
		HttpSession session = request.getSession();
		String customerId = (String)session.getAttribute("id");
		
		// 로그인이 되어있지 않습니다.
		if(customerId == null) {
			result.sendError(401, "");
			
			return result;
		}
		
		// 상품 코드를 가져옵니다.
		int productId = Integer.parseInt(request.getParameter("productId"));
		
		InterestService service = new InterestService();
		boolean isOK = service.deleteInterest(customerId, productId);
		
		// 상태 JSON을 반환합니다.
		JSONObject status = new JSONObject();
		
		// 상태 값이 OK라면 success를 반환합니다.
		// 그게 아니라면 오류를 반환합니다.
		if(isOK) {
			status.put("status", "success");	
		} else {
			result.sendError(402, "");
			
			return result;
		}
		
		// JSON 값을 반환합니다.
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		
		PrintWriter out = response.getWriter();
		out.println(status.toJSONString());
		
		return null;
	}
}
