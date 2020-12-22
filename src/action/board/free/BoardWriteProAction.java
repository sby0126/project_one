package action.board.free;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import service.board.free.BoardWriteProService;
import vo.board.free.ActionForward;
import vo.board.free.BoardBean;
import vo.board.free.Board_file;

public class BoardWriteProAction implements Action {

   
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
         ActionForward forward=null;
         String saveFolder="C:\\Users\\User\\Desktop\\fileUpload";
         int fileSize=5*1024*1024;      
         MultipartRequest multi=new MultipartRequest(request,
             saveFolder,
               fileSize,
               "UTF-8",
               new DefaultFileRenamePolicy());
         BoardBean boardBean = new BoardBean();     
         ArrayList<Board_file> board_file_list = new ArrayList<Board_file>();
         boardBean.setName(multi.getParameter("name"));
         boardBean.setPass(multi.getParameter("pass"));
         boardBean.setSubject(multi.getParameter("subject"));
         boardBean.setContent(multi.getParameter("content"));         
         Enumeration fileNames = multi.getFileNames();
         while(fileNames.hasMoreElements()) {
            String name= (String)fileNames.nextElement(); 
            board_file_list.add(new Board_file(multi.getOriginalFileName(name),multi.getFilesystemName(name)));
         }
                 
           BoardWriteProService boardWriteProService = new BoardWriteProService();
           boolean isWriteSuccess = boardWriteProService.registArticle(boardBean,board_file_list);
          

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

