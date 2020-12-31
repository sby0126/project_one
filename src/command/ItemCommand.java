package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import action.ActionResult;
import service.ContentsSearchService;
import service.ItemService;

public class ItemCommand extends Command {

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
		String category = request.getParameter("category");
		String ages = request.getParameter("ages");		
		
		String keyword = request.getParameter("keyword");
		
		int start = Integer.parseInt(request.getParameter("start"));
		int end = Integer.parseInt(request.getParameter("end"));
		
		if(!pageType.equals("item")) {
			return null;
		}
		
		ItemService itemService = new ItemService();
		JSONObject data = new JSONObject();
		
		String id = getUserId(request);
		
		if(keyword != null) {
			ContentsSearchService sv = new ContentsSearchService();	
			data = sv.getItem(pageType, genderType, shopType, category, ages, keyword, id, start, end);
		} else {		
			data = itemService.getItem(pageType, genderType, shopType, category, ages, id, start, end);	
		}
		
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(data.toJSONString());
		
		return null;
	}
}
