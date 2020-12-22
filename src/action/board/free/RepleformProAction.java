package action.board.free;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.board.free.Action;
import service.board.free.BoardWriteProService;
import vo.board.free.ActionForward;
import vo.board.free.BoardBean;
import vo.board.free.Re_boardBean;

public class RepleformProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		  ActionForward forward=null;
	      Re_boardBean re_boardBean = null;
	      re_boardBean = new Re_boardBean();
	      re_boardBean.setRe_name(request.getParameter("name"));
	      re_boardBean.setRe_content(request.getParameter("content"));
	      re_boardBean.setRe_num2(Integer.parseInt(request.getParameter("num_2")));
	      String nowPage= request.getParameter("page");
	      String board_num = request.getParameter("num");
	      
	      

	      BoardWriteProService boardWriteProService = new BoardWriteProService();
	      boolean isWriteSuccess = boardWriteProService.registReArticle(re_boardBean);

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
	         forward.setPath("boardDetail.abc?page=" + nowPage+"&board_num=" + board_num);
	         
	        
	         
	      }

	      return forward;
	
	}

}
