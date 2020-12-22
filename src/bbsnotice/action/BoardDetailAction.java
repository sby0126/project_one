package bbsnotice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbsnotice.ActionForward;
import bbsnotice.BoardBean;
import bbsnotice.svc.BoardDetailService;

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
	    forward.setPath("/bbs/bbsnotice/qna_board_view.jsp");
	    
	    return forward;
	}
	
	

}
