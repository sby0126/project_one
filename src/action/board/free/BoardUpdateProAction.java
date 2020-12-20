package action.board.free;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.board.free.BoardReplyProService;
import service.board.free.BoardUpdateProService;
import vo.board.free.ActionForward;
import vo.board.free.BoardBean;

public class BoardUpdateProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		 request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		BoardBean article = new BoardBean();
		String nowPage = request.getParameter("page");
		String board_num = request.getParameter("num");
		article.setNum(Integer.parseInt(request.getParameter("num")));
		article.setPass(request.getParameter("pass"));
		article.setRe_ref(Integer.parseInt(request.getParameter("ref")));
		article.setRe_lev(Integer.parseInt(request.getParameter("lev")));
		article.setRe_seq(Integer.parseInt(request.getParameter("seq")));
		article.setName(request.getParameter("name"));
		article.setSubject(request.getParameter("subject"));
		article.setContent(request.getParameter("content"));
		BoardUpdateProService boardUpdateProService  = new BoardUpdateProService();
		boolean updateSuccess = boardUpdateProService.updateArticle(article);
		
			if(updateSuccess) {
	             forward.setPath("/boardDetail.abc?page=" + nowPage+"&board_num=" + board_num);
			}
		
		return forward;
	}

}
