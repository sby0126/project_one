package core.board.qna;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteCommand extends Command {
	
	
	public String authority = "admin";
	
	public DeleteCommand(BoardDAO board) {
		super(board);
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int articleID = Integer.parseInt(request.getParameter("postNumber"));
		
		// 세션의 존재 확인
		HttpSession session = request.getSession();
		String id = String.valueOf(session.getAttribute("id"));
		
		// 세션이 존재하지 않으면 오류
		if(id == null) {
			response.sendError(405, "게시물을 삭제할 권한이 존재하지 않습니다.");
			return;
		}
		
		// 관리자 또는 자신만 게시물을 삭제할 수 있게 하는 로직
		if(id != null || id.equals("admin")) {
			// 게시물 삭제 질의문 실행
			
			System.out.println("게시물을 삭제합니다.");
			
			boardMgr.deletePost(articleID);
			response.sendRedirect("pages/board-default.jsp");
			return;
		}
		
	}
}
