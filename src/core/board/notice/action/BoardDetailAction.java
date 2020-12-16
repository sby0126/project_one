package core.board.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.board.notice.svc.BoardDetailService;
import core.board.notice.ActionForward;
import core.board.notice.BoardBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
	    String page = request.getParameter("page");
	    BoardDetailService boardDetailService = new BoardDetailService();
	    BoardBean article = boardDetailService.getArticle(board_num);
	    ActionForward forward = new ActionForward();
	    request.setAttribute("page", page);
	    request.setAttribute("article", article);
	    forward.setPath("/board/qna_board_view.jsp");
	    
	    return forward;
	}
	
	

}
