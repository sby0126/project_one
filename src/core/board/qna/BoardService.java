package core.board.qna;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

/**
 * Servlet implementation class BoardService
 */
@WebServlet("/board/qna/*")
public class BoardService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private BoardDAO boardMgr;
    
    private WriteFormCommand writeCommand;
    private PostViewCommand postViewCommand;
    
    public BoardService() {
        super();
        boardMgr = new BoardDAO();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	writeCommand = new WriteFormCommand(boardMgr);
    	postViewCommand = new PostViewCommand(boardMgr);
    	
    	initWithDefaultUploadFolder(config.getServletContext().getRealPath("uploads"));
    }
    
    public void initWithDefaultUploadFolder(String uploadsFolderPath) {
    	try {
    		File uploadsFolder = new File(uploadsFolderPath);
    		
    		if(!uploadsFolder.exists()) {
    			uploadsFolder.mkdir();
    		}
    				
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String currentPage = request.getPathInfo();
		String nextPage = "pages/board-default.jsp";
		
		// 모든 게시물을 읽어서 JSON으로 내보냅니다.
		if(currentPage.equals("/listAll.do")) {
			JSONArray json = boardMgr.getListAll();
			
			response.setContentType("application/json");
			response.setCharacterEncoding("EUC-KR");
			
			PrintWriter out = response.getWriter();
			out.println(json.toJSONString());
			
		} else if(currentPage.equals("/postView.do")) { 
			postViewCommand.execute(request, response);
		} else if(currentPage.equals("/writeForm.do")) {
			writeCommand.execute(request, response);
		} else if(currentPage.equals("/writeReply.do")) {
			// 댓글 작성
		} else if(currentPage.equals("/updateReply.do")) { 
			// 댓글 업데이트
		} else if(currentPage.equals("/deleteReply.do")) {
			// 댓글 삭제
		} else if(currentPage.equals("/imageUpload.do")) {
			
		}
		
	}

}
