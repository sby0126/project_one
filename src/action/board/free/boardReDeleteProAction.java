package action.board.free;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.board.free.BoardDeleteProService;
import vo.board.free.ActionForward;

public class boardReDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		String re_num = request.getParameter("re_num");
		String num = request.getParameter("num");
		String nowPage = request.getParameter("nowPage");
		BoardDeleteProService boardDeleteProService = new BoardDeleteProService();
		boolean isRemoveSuccess = boardDeleteProService.removeReArticle(re_num);
		
		
		if(!isRemoveSuccess){
			 
	        response.setContentType("text/html;charset=UTF-8");
	        PrintWriter out=response.getWriter();
	        out.println("<script>");
	        out.println("alert('삭제실패');");
	        out.println("history.back();");
	        out.println("</script>");
	        out.close();
		}else{
			
            forward = new ActionForward();
            forward.setRedirect(true);
            forward.setPath("boardDetail.abc?page=" + nowPage+"&board_num=" + num);
		}
		         
		  return forward;
	}
		


}
