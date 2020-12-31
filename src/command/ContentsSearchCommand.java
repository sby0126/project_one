package command;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import action.ActionResult;
import service.ContentsSearchService;

/**
 * 아이템 검색 기능을 제공합니다.
 */
public class ContentsSearchCommand extends Command {
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		result.start(request, response);
		
		// 기본 매개변수 파싱
		String pageType = request.getParameter("pageType");
		String genderType = request.getParameter("gndr");
		String shopType = request.getParameter("shopType");
		String category = request.getParameter("category");
		String ages = request.getParameter("ages");		
		String keyword = request.getParameter("keyword");
		
		int start = Integer.parseInt(request.getParameter("start"));
		int end = Integer.parseInt(request.getParameter("end"));
		
		// 시간 값 획득
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		String timeText = format.format(time);
		
		// 컨텐츠 서비스 생성
		ContentsSearchService service = new ContentsSearchService();
		boolean isOK = service.searchKeyword(keyword,  timeText);
		
		JSONObject data = new JSONObject();
		
		// 키워드가 정상적으로 삽입되었는가?
		if(isOK) {
			data = service.getItem(pageType, genderType, shopType, category, ages, keyword, null, start, end);
		}
		
		// JSON으로 내보냄.
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println(data.toJSONString());
		
		return null;
	}
}
