package core.board.qna;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WriteFormCommand {
	
	private BoardDAO boardMgr;
	private boolean isReady = false;
	
	public WriteFormCommand(BoardDAO boardDAO)  {
		this.boardMgr = boardDAO;
		isReady = true;
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!isReady) {
			return;
		}
		
		String nextPage = "pages/board-default.jsp";
		
		// 게시물 작성
		// 게시물을 작성할 때, /board/qna/writeForm.do?postNumber=1로 POST 데이터를 AJAX를 통해 전달합니다.
		int postNumber = 0;
		
		// 글 번호가 있으면 글 번호를 매개변수로부터 파싱합니다.
		if(request.getParameter("postNumber") != null) {
			postNumber = Integer.parseInt(request.getParameter("postNumber"));	
		}
		
		// 파서를 생성하여 JSON을 파싱합니다.
		JSONParser parser = new JSONParser();
		try {
			
			// JSON 데이터를 받으려면 getInputStream()을 사용해야 합니다.
			// getParameter()는 사용 불가능합니다.
			
			InputStream inputStream = request.getInputStream();

			// 데이터가 들어오지 않았을 때 오류 처리
			if(inputStream == null) {
				request.setAttribute("errorMessage", "데이터가 잘못되었습니다.");
				request.setAttribute("url", "/pages/board-default.jsp");
				nextPage = "/pages/error.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
				dispatcher.forward(request, response);
				return;		
			}
			
			// 버퍼로 문자열을 입력 받습니다.
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
				nextPage = "/pages/error.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
				dispatcher.forward(request, response);
			}			
							
			JSONObject data = (JSONObject) parser.parse ( URLDecoder.decode(raw, "UTF-8") );
			HttpSession session = request.getSession();
			
			String authorID = (String)session.getAttribute("id");
			
			System.out.println("현재 ID : " + authorID);
			
			if(authorID == null || authorID.equals("")) {
				request.setAttribute("errorMessage", "로그 인되어있지 않습니다.");
				request.setAttribute("url", "/pages/board-default.jsp");
				nextPage = "/pages/error.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
				dispatcher.forward(request, response);
				return;
			}
			
			String title = String.valueOf( data.get("title") );
			String contents = String.valueOf( data.get("contents") );
			
			boolean ret = boardMgr.writePost(authorID, "Normal", title, contents, null);
			
			response.sendRedirect("/pages/board-default.jsp");
			return;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
}
