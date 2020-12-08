package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import action.ActionResult;
import service.ShopService;

public class ShopCommand extends Command {

	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		String pageType = request.getParameter("pageType");
		String genderType = request.getParameter("gndr");
		String shopType = request.getParameter("shopType");
		
		if(!pageType.equals("shop")) {
			return null;
		}
		
		ShopService shopService = new ShopService();
		JSONObject data = shopService.getShop(pageType, genderType, shopType);
		
		response.setCharacterEncoding("EUC-KR");
		
		PrintWriter out = response.getWriter();
		out.println(data.toJSONString());
		
		return null;
	}
}
