package bbsnotice.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< Updated upstream:src/core/board/notice/action/BoardReplyProAction.java
import svc.BoardReplyProService;
import vo.ActionForward;
import vo.BoardBean;
=======
import bbsnotice.BoardReplyBean;
import bbsnotice.ActionForward;
>>>>>>> Stashed changes:src/bbsnotice/action/BoardReplyProAction.java

public class BoardReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = null;
        String nowPage = request.getParameter("page");
        String ctxtno = request.getParameter("ctxtno");
        BoardReplyBean article = new BoardReplyBean();        
        
        article.setReplynm(request.getParameter("replynm"));
        article.setReplypwd(request.getParameter("replypwd"));
        article.setReplyctxt(request.getParameter("replyctxt"));
        article.setRef(Integer.parseInt(request.getParameter("ctxtno")));
        article.setDepth(Integer.parseInt(request.getParameter("depth")));
        article.setReplypos(Integer.parseInt(request.getParameter("replypos")));            
        
        boolean isReplySuccess = false;
        
          if(isReplySuccess){
             forward = new ActionForward();
             forward.setRedirect(true);
             forward.setPath("boardList.bo?page=" + nowPage);
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
