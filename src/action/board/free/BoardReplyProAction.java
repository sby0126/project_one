package action.board.free;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.board.free.BoardReplyProService;
import vo.board.free.ActionForward;
import vo.board.free.BoardBean;

public class BoardReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
        String nowPage = request.getParameter("page");
        BoardBean article = new BoardBean();        
        article.setNum(Integer.parseInt(request.getParameter("BOARD_NUM")));
        article.setName(request.getParameter("name"));
        article.setPass(request.getParameter("pass"));
        article.setSubject(request.getParameter("subject"));
        article.setContent(request.getParameter("content"));
        article.setRe_ref(Integer.parseInt(request.getParameter("BOARD_RE_REF")));
        article.setRe_lev(Integer.parseInt(request.getParameter("BOARD_RE_LEV")));
        article.setRe_seq(Integer.parseInt(request.getParameter("BOARD_RE_SEQ")));            
        BoardReplyProService boardReplyProService = new BoardReplyProService();
        boolean isReplySuccess = boardReplyProService.replyArticle(article);
        
          if(isReplySuccess){
             forward = new ActionForward();
             forward.setRedirect(true);
             forward.setPath("boardList.abc?page=" + nowPage);
          }
          else{
             response.setContentType("text/html;charset=UTF-8");
             PrintWriter out = response.getWriter();
             out.println("<script>");
             out.println("alert('답장실패')");
             out.println("history.back()");
             out.println("</script>");
          }
          
          return forward;
		
	}

}
