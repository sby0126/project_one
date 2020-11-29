package core.board.qna;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class BoardService
 */
@WebServlet("/board/qna/*")
public class BoardService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private BoardDAO boardMgr;
    
    public BoardService() {
        super();
        boardMgr = new BoardDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String currentPage = request.getPathInfo();
		String nextPage = "pages/board-default.jsp";
		
		// 모든 게시물을 읽어서 JSON으로 내보냅니다.
		if(currentPage.equals("/listAll.do")) {
			JSONArray json = boardMgr.getListAll();
			
			response.setContentType("application/json");
			response.setCharacterEncoding("EUC-KR");
			
			PrintWriter out = response.getWriter();
			out.println(json.toJSONString());
			
		} else if(currentPage.equals("/postView.do")) { 
			// 게시물을 JSON으로 읽어옵니다. 
			
			int postNumber = Integer.valueOf(request.getParameter("postNumber"));
			
			// 조회수를 1 증가시킵니다.
			boardMgr.updatePostViewCount(postNumber);
			
			// 데이터를 읽어옵니다.
			JSONArray arr = boardMgr.readPost(postNumber);
			
			if(arr == null) {
				response.sendError(404, "Can't find a data");
				
				return;
			}
			
			JSONObject json = ((JSONObject)arr.get(0));
			response.setContentType("application/json");
			response.setCharacterEncoding("EUC-KR");
			
			PrintWriter out = response.getWriter();
			
			out.println(json.toJSONString());
			
		} else if(currentPage.equals("/writeForm.do")) {
			int postNumber = 0;
			
			if(request.getParameter("postNumber") != null) {
				postNumber = Integer.parseInt(request.getParameter("postNumber"));	
			}
			
			JSONParser parser = new JSONParser();
			try {
				String raw = request.getParameter("data");
				if(raw == null) {
					request.setAttribute("errorMessage", "데이터가 잘못되었습니다.");
					request.setAttribute("url", "/pages/board-default.jsp");
					nextPage = "/pages/error.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
					dispatcher.forward(request, response);
					return;
				}
				
				JSONObject data = (JSONObject) parser.parse ( request.getParameter("data") );
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
				String content = String.valueOf( data.get("content") );
				
				boolean ret = boardMgr.writePost(authorID, "qna", title, content, null);
				
				if(ret) {
					response.sendRedirect("/pages/board-default.jsp");
				} else {
					nextPage = "/pages/board-default.jsp";
					RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
					dispatcher.forward(request, response);					
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
