package command;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import action.ActionResult;
import service.CookieService;
import service.RecentlyItemService;
import vo.ProductVO;

public class RecentlyItemsCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		// Create a cookie service.	
		CookieService cookieService = new CookieService();
		HashMap<String, String> cookie = cookieService.getKeyValue(request);
		String value = cookie.get("recentlyItems");
		// String value = "0";
		
		String num = "";
		
		if(value != null) {
			num = URLDecoder.decode(value);
		} else {
			value = "0";
		}
		
		Vector<ProductVO> list = null;
		
		if(!value.equals("0")) {
			RecentlyItemService service = new RecentlyItemService(num);
			list = service.getCards();
		}
		
		JSONObject root = new JSONObject();
		
		list.forEach(i -> {
			
		});
		
		
		// JSON으로 내보냄.
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(root.toJSONString());
		
		
		return result;
	}
	
}
