package action.board.free;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.board.free.BoardUpdateProService;
import vo.board.free.ActionForward;
import vo.board.free.Re_boardBean;

public class replePlayUpdateProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		Re_boardBean re_article = new Re_boardBean();
		String nowPage = request.getParameter("page");
		String board_num = request.getParameter("articleNum");
		re_article.setRe_num(Integer.parseInt(request.getParameter("num")));
		re_article.setRe_content(request.getParameter("content"));
		BoardUpdateProService boardUpdateProService  = new BoardUpdateProService();
		boolean updateSuccess = boardUpdateProService.updateReArticle(re_article);
		
			if(updateSuccess) {
				 forward.setRedirect(true);
	             forward.setPath("boardDetail.abc?page=" + nowPage + "&board_num=" + board_num );
			}
		
		return forward;
	}


}
