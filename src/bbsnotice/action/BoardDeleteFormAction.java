package bbsnotice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbsnotice.ActionForward;
import bbsnotice.BoardBean;
import bbsnotice.svc.BoardDetailService;

public class BoardDeleteFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();
        String nowPage = request.getParameter("page");
        int board_num = Integer.parseInt(request.getParameter("board_num"));
        BoardDetailService boardDetailService = new BoardDetailService();
        BoardBean article = boardDetailService.getArticle(board_num);   
        request.setAttribute("article", article);
        request.setAttribute("page", nowPage);
        forward.setPath("/bbs/bbsnotice/qna_board_delete.jsp");
        
        return forward;
	}

}
