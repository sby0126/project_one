package core.board.notice.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import core.board.notice.svc.BoardWriteProService;
import core.board.notice.ActionForward;
import core.board.notice.BoardBean;

public class BoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward=null;
	    BoardBean boardBean = null;
	    String realFolder="";
	    String saveFolder="/boardUpload";
	    int fileSize=5*1024*1024;
	    ServletContext context = request.getServletContext();
	    realFolder=context.getRealPath(saveFolder);         
	    MultipartRequest multi = new MultipartRequest(request,realFolder,fileSize, "UTF-8",
	            new DefaultFileRenamePolicy());
	    boardBean = new BoardBean();
	    boardBean.setWrtnm(multi.getParameter("wrtnm"));
	    boardBean.setCpwd(multi.getParameter("cpwd"));
	    boardBean.setCtitle(multi.getParameter("ctitle"));
	    boardBean.setCtxt(multi.getParameter("ctxt"));
	    boardBean.setFilename(
	    multi.getOriginalFileName((String)multi.getFileNames().nextElement()));
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
	       forward.setPath("boardList.bo");
	    }

	    return forward;
	      
	}
}
