package action.board.free;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.board.free.BoardReplyProService;
import vo.board.free.ActionForward;
import vo.board.free.BoardBean;
import vo.board.free.Re_boardBean;

public class ReplePlayProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ActionForward forward = null;
        String nowPage = request.getParameter("page");
        String board_num = request.getParameter("articleNum");
        
        Re_boardBean re_article = new Re_boardBean();        
        re_article.setRe_num(Integer.parseInt(request.getParameter("num")));
        re_article.setRe_name(request.getParameter("name"));
        re_article.setRe_num2(Integer.parseInt(request.getParameter("num2")));
        re_article.setRe_content(request.getParameter("content"));
        re_article.setRe_re_ref(Integer.parseInt(request.getParameter("ref")));
        re_article.setRe_re_lev(Integer.parseInt(request.getParameter("lev")));
        re_article.setRe_re_seq(Integer.parseInt(request.getParameter("seq")));   
  
        
        BoardReplyProService boardReplyProService = new BoardReplyProService();
        boolean isReplySuccess = boardReplyProService.replyReArticle(re_article);
        
          if(isReplySuccess){
             forward = new ActionForward();
             forward.setRedirect(true);
             forward.setPath("boardDetail.abc?page=" + nowPage + "&board_num=" + board_num );
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
