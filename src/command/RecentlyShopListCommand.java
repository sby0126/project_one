package command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import action.ActionResult;
import dao.ContentDAO;
import vo.ProductVO;

public class RecentlyShopListCommand extends Command {
	@SuppressWarnings("unchecked")
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		result.start(request, response);
		
		// 원시 JSON 데이터를 받아옵니다.
		InputStream inputStream = request.getInputStream();
		
		// 데이터가 들어오지 않았을 때 오류 처리
		if(inputStream == null) {
			request.setAttribute("errorMessage", "데이터가 잘못되었습니다.");
			request.setAttribute("url", "/pages/board-default.jsp");
			String nextPage = "/pages/error.jsp";
			result.forward(nextPage);
			return result;
		}
		
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream, "UTF-8") );
		char[] buf = new char[1024];
		int bytesRead = -1; 
		StringBuilder sb = new StringBuilder();
		
		while((bytesRead = bufferedReader.read(buf)) != -1) {
			sb.append(buf, 0, bytesRead);
		}
		
		// 문자열 조합 결과, 아무 데이터도 없을 때 오류 처리
		String raw = sb.toString();
			
		if(raw == null) {
			request.setAttribute("errorMessage", "문자열 데이터가 없습니다.");
			request.setAttribute("url", "/pages/board-default.jsp");
			String nextPage = "/pages/error.jsp";
			result.forward(nextPage);
			return result;
		}				
		
		// JSON 파싱
		JSONParser parser = new JSONParser();
		Object objRaw = null;
		try {
			objRaw = parser.parse( URLDecoder.decode(raw, "UTF-8") );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// JSON 생성
		JSONObject json = (JSONObject)objRaw;
		String primitiveRaw = (String)json.get("recentShopItems");
		
		// 내부에 있는 값을 읽습니다.
		List<String> token = Arrays.asList(primitiveRaw.split(","));
		
		// 정수형으로 변환합니다.
		IntStream t = token.stream().mapToInt(Integer::new);
		
		
		List<Integer> recentlyList = t.boxed().collect(Collectors.toList());
		
		// 최근 샵 카드 리스트를 받아옵니다.
		List<ProductVO> productList = new ArrayList<>();
		
		ContentDAO contentDAO = ContentDAO.getInstance();
		
		recentlyList.forEach(id -> {
			productList.add( contentDAO.findShopDataAsID(id) );	
		});
		
		// 오프셋 생성
		JSONObject offset = new JSONObject();
		
		offset.put("start", 0);
		offset.put("count", 68);
				
		
		// JSON으로 변환 시작합니다.
		JSONObject root = new JSONObject();
		root.put("pageType", "shop" );
		root.put("genderType", 'M' );
		root.put("shopType", 'S' );
		root.put("offset", offset);		
		root.put("imageUrl", "https://drive.google.com/uc?export=view&id=");	
		
		JSONArray contentData = new JSONArray();
		JSONObject imageData = new JSONObject();
		
		root.put("contentData", contentData);
		root.put("imageData", imageData);
		
		JSONArray jsonArray = new JSONArray();
		
		for(ProductVO vo : productList) {				
			JSONObject newContentData = new JSONObject();
			
			newContentData.put("category", vo.getShoptype());
			newContentData.put("shopName", vo.getShopname());
			newContentData.put("texts", vo.getTexts());
			newContentData.put("url", vo.getContenturl());
			newContentData.put("id", vo.getId());
			newContentData.put("link", vo.getLink());
			
			contentData.add(newContentData);
		}
				
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		
		PrintWriter out = response.getWriter();
		out.println(jsonArray.toJSONString());
	
		return null;
	}
}
