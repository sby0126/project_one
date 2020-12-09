package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import action.ActionResult;
import service.ItemService;

public class ItemCommand extends Command {

	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		String pageType = request.getParameter("pageType");
		String genderType = request.getParameter("gndr");
		String shopType = request.getParameter("shopType");
		String category = request.getParameter("category");
		String ages = request.getParameter("ages");		
		
		if(!pageType.equals("item")) {
			return null;
		}
		
		ItemService itemService = new ItemService();
		JSONObject data = itemService.getItem(pageType, genderType, shopType, category, ages);
		
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(data.toJSONString());
		
		return null;
	}
}
