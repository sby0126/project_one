package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import action.ActionResult;
import service.MyShopService;

/**
 * 마이샵을 추가합니다.
 */
public class AddMyShopCommand extends Command {
	
	@SuppressWarnings("unchecked")
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		HttpSession session = request.getSession();
		
		// 로그인이 되어있지 않으면 401 오류를 냅니다.
		if(session.getAttribute("id") == null) {
			result.sendError(401, "");
			return result;
		}
		
		String customerID = (String)session.getAttribute("id");
		int shopId = Integer.parseInt(request.getParameter("shopId"));
		
		MyShopService service = new MyShopService();
		boolean isOK = service.addMyShop(customerID, shopId);
		
		// 상태 정보를 반환합니다.
		JSONObject statusText = new JSONObject();
		
		if(isOK) {
			statusText.put("status", "success");
			statusText.put("customerID", customerID);
			statusText.put("shopId", shopId);
		} else {
			result.sendError(402, "");
			return result;
		}
		
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(statusText.toJSONString());
		
		return null;
	}
}
