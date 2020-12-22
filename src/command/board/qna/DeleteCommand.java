package command.board.qna;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.ActionResult;
import command.Command;

public class DeleteCommand extends Command {
	
	
	public String authority = "admin";
	
	public DeleteCommand() {
		super();
	}
	
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		result.start(request, response);
		
		int articleID = Integer.parseInt(request.getParameter("postNumber"));
		
		// 세션의 존재 확인
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		// 세션이 존재하지 않으면 오류
		if(id == null) {
			result.sendError(405, "게시물을 삭제할 권한이 존재하지 않습니다.");
			return result;
		}
		
		// 관리자 또는 자신만 게시물을 삭제할 수 있게 하는 로직
		if(id != null || id.equals("admin")) {
			// 게시물 삭제 질의문 실행
			
			System.out.println("게시물을 삭제합니다.");
			
			getDAO().deletePost(articleID);
			 
			// 리다이렉션 시 컨텍스트 경로까지 전달해야 무한 반복이 걸리지 않습니다.
			result.sendRedirect(request.getContextPath() + "/pages/board-default.jsp");
			return result;
		}
		
		return null;
	}
}
