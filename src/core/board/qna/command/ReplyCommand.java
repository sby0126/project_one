package core.board.qna.command;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.board.qna.BoardDAO;

public class ReplyCommand extends Command {
	
	public ReplyCommand(BoardDAO boardDAO)  {
		super(boardDAO);
	}
	
	public void write(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int parentArticleID = Integer.parseInt(request.getParameter("postNumber"));
		
		HttpSession session = request.getSession();
		String authorID = (String)session.getAttribute("id");
		
		String content = request.getParameter("contents");
		
		
		if(authorID == null) {
			request.setAttribute("_status", "error");
			request.setAttribute("errorMessage", "댓글을 작성하려면 우선 로그인을 하셔야 합니다.");
			request.setAttribute("url", "/pages/board-default.jsp");
			request.setAttribute("nextpage", "/pages/error.jsp");
			return;
		}
		
		boardMgr.writeComment(parentArticleID, authorID, content);
		
		String redirect = response.encodeRedirectURL(request.getContextPath() + "/pages/board-post.jsp?postNumber=" + parentArticleID);
		response.sendRedirect(redirect);
		return;
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
