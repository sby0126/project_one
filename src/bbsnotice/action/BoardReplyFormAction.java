package core.board.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.board.notice.svc.BoardDetailService;
import vo.board.notice.ActionForward;
import vo.board.notice.BoardBean;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
        String nowPage = request.getParameter("page");
        int board_num = Integer.parseInt(request.getParameter("board_num"));
        BoardDetailService boardDetailService = new BoardDetailService();
        BoardBean article = boardDetailService.getArticle(board_num);   
        request.setAttribute("article", article);
        request.setAttribute("page", nowPage);
        forward.setPath("/board/qna_board_reply.jsp");
        
        return forward;
        
	}

}
