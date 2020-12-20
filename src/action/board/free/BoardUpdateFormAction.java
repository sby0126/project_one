package action.board.free;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.board.free.BoardDetailService;
import vo.board.free.ActionForward;
import vo.board.free.BoardBean;

public class BoardUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward=new ActionForward();
	    BoardBean article = new BoardBean();
	    int board_num=Integer.parseInt(request.getParameter("board_num"));
	    String page = request.getParameter("page");
	    BoardDetailService boardDetailService = new BoardDetailService();
	    article = boardDetailService.getArticle(board_num);
	    request.setAttribute("page", page);
        request.setAttribute("article", article);
        forward.setPath("/board/update.jsp");
        return forward;
	    
	    
		
	}

}
