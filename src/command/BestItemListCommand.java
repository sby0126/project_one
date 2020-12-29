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
import service.BestItemListService;
import vo.SearchVO;

/**
 * 인기 검색어 목록을 반환합니다.
 */
public class BestItemListCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		BestItemListService service = new BestItemListService();
		List<SearchVO> searchList = service.getList();
		
		JSONArray list = new JSONArray();
		
		// JSON으로 변환합니다.
		for(SearchVO vo : searchList) {
			JSONObject data = new JSONObject();
			data.put("keyword", vo.getKeyword());
			data.put("count", vo.getCount());
			
			list.add(data);
		}
		
		// JSON으로 내보냅니다.
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(list.toJSONString());
		
		return null;
	}
}
