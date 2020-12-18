package command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import action.ActionResult;
import service.ShopService;

public class SearchShopItemCommand extends Command {
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		// 상점 서비스 객체를 생성합니다.
		ShopService service = new ShopService();
		
		// 페이지 타입을 가져옵니다.
		String pageType = request.getParameter("pageType");
		
		// ID 값을 할당합니다.
		int id = Integer.parseInt(request.getParameter("id"));
				
		// JSON으로 변환합니다.
		JSONObject data = service.getItemForCertainShop(pageType, id);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");		
		
		if( data != null ) {
			// 한글이 나올 수 있게 처리합니다 (순서 문자열 인코딩 변환 -> 프린트라이터)
			PrintWriter out = response.getWriter();
			out.println(data.toJSONString());
						
		} else {
			response.sendError(403, "");
		}
		

		return null;
	}
}
