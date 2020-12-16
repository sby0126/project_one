package freeBoard.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import freeBoard.svc.BoardWriteProService;
import freeBoard.vo.ActionForward;
import freeBoard.vo.BoardBean;

public class BoardWriteProAction implements Action {

   
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      ActionForward forward=null;
      BoardBean boardBean = null;
      boardBean = new BoardBean();
      boardBean.setName(request.getParameter("name"));
      boardBean.setPass(request.getParameter("pass"));
      boardBean.setSubject(request.getParameter("subject"));
      boardBean.setContent(request.getParameter("content"));

      BoardWriteProService boardWriteProService = new BoardWriteProService();
      boolean isWriteSuccess = boardWriteProService.registArticle(boardBean);

      if(!isWriteSuccess){
         response.setContentType("text/html;charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println("<script>");
         out.println("alert('등록실패')");
         out.println("history.back();");
         out.println("</script>");
      }
      else{
         forward = new ActionForward();
         forward.setRedirect(true);
         forward.setPath("boardList.abc");
      }

      return forward;
      
   }     
}


