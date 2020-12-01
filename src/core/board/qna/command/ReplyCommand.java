package core.board.qna.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.board.qna.BoardDAO;

public class ReplyCommand extends Command {
		
	class Parameters {
		public String authorID;
		public int parentArticleID;
		public String contents;
		public String methods;
		public int depth;
		public int parentID;
		public int pos;
	}
	
	private Parameters param = new Parameters();
	
	public ReplyCommand(BoardDAO boardDAO)  {
		super(boardDAO);
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession();
		
		param.authorID = (String)session.getAttribute("id");
		param.parentArticleID = Integer.parseInt(request.getParameter("postNumber"));;
		param.contents = request.getParameter("contents");
		param.methods = request.getParameter("method");
		param.depth = Integer.parseInt(request.getParameter("depth"));
		param.parentID = Integer.parseInt(request.getParameter("parentID"));
		param.pos = Integer.parseInt(request.getParameter("pos"));
		
		switch( param.methods ) {
		case "write":
			write(request, response);
			break;
		case "delete":
			delete(request, response);
			break;
		case "update":
			update(request, response);
			break;
		case "writeChild":
			writeChild(request, response);
		}
		
		if(param.authorID == null) {
			request.setAttribute("_status", "error");
			request.setAttribute("errorMessage", "댓글을 작성하려면 우선 로그인을 하셔야 합니다.");
			request.setAttribute("url", "/pages/board-default.jsp");
			request.setAttribute("nextpage", "/pages/error.jsp");
			return;
		}
		
		String redirect = response.encodeRedirectURL(request.getContextPath() 
				+ "/pages/board-post.jsp?postNumber=" 
				+ param.parentArticleID);
		
		response.sendRedirect(redirect);
		return;
	}
	
	public void write(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boardMgr.writeComment(
					param.parentArticleID, 
					param.authorID, 
					param.contents
				);
	}	
	
	public void writeChild(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}	
	
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
}
