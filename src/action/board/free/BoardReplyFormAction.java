package action.board.free;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.board.free.BoardDetailService;
import vo.board.free.ActionForward;
import vo.board.free.BoardBean;

public class BoardReplyFormAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
        String nowPage = request.getParameter("page");
        int board_num=Integer.parseInt(request.getParameter("board_num"));
        BoardDetailService boardDetailService = new BoardDetailService();
        BoardBean article=boardDetailService.getArticle(board_num);   
        request.setAttribute("article", article);
        request.setAttribute("page", nowPage);
        forward.setPath("/board/reply.jsp");
        return forward;
        
	
	}

}
