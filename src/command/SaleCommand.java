package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import action.ActionResult;
import service.SaleService;

public class SaleCommand extends Command {

	public String getUserId(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		return (String)session.getAttribute("id");
	}	
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		String pageType = request.getParameter("pageType");
		String genderType = request.getParameter("gndr");
		String shopType = request.getParameter("shopType");
		
		String keyword = request.getParameter("keyword");
		
		int start = Integer.parseInt(request.getParameter("start"));
		int end = Integer.parseInt(request.getParameter("end"));
						
		if(!pageType.equals("sale")) {
			return null;
		}
		
		// ID 값을 가져옵니다.
		String id = getUserId(request);
		
		SaleService saleService = new SaleService();
		JSONObject data = saleService.getSale(pageType, genderType, shopType, id, start, end);
		
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(data.toJSONString());
		
		return null;
	}
}
