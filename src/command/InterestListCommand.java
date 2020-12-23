package command;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import action.ActionResult;
import service.InterestService;
import vo.InterestVO;

public class InterestListCommand extends Command {

	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String customerId = (String)request.getSession().getAttribute("id");
		
		if(customerId == null) {
			result.sendError(401, "");
			
			return result;
		}
		
		// 관심 상품을 리스트 형식으로 취득합니다.
		InterestService service = new InterestService();
		List<InterestVO> list = service.getInterest(customerId);
		
		JSONArray array = new JSONArray();
		
		for(InterestVO vo : list) {
			JSONObject c = new JSONObject();
			
			c.put("productId", vo.getProductId());
			
			array.add(c);
		}
		
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(array.toJSONString());
		
		return null;
	}
}
