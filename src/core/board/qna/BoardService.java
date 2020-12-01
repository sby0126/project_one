package core.board.qna;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import core.board.qna.command.DeleteCommand;
import core.board.qna.command.ImageUploadCommand;
import core.board.qna.command.PostViewCommand;
import core.board.qna.command.ReplyCommand;
import core.board.qna.command.WriteFormCommand;

/**
 * Servlet implementation class BoardService
 */
@WebServlet("/board/qna/*")
public class BoardService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private BoardDAO boardMgr;
    
    private WriteFormCommand writeCommand;
    private PostViewCommand postViewCommand;
    private ReplyCommand replyCommand;
    
    private String saveFolderName;
    
    public BoardService() {
        super();
        boardMgr = new BoardDAO();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	writeCommand = new WriteFormCommand(boardMgr);
    	postViewCommand = new PostViewCommand(boardMgr);
    	replyCommand = new ReplyCommand(boardMgr);
    	
    	initWithDefaultUploadFolder(config.getServletContext().getRealPath("uploads"));
    }
    
    public void initWithDefaultUploadFolder(String uploadsFolderPath) {
    	try {
    		File uploadsFolder = new File(uploadsFolderPath);
    		saveFolderName = uploadsFolderPath;
    		
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
		
		if(!boardMgr.isExistsTable()) {
			request.setAttribute("errorMessage", "테이블이 존재하지 않습니다. 먼저 테이블을 생성해주세요.");
			request.setAttribute("url", "/pages/board-default.jsp");
			nextPage = "/pages/error.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
			
			return;
		}
		
		try {
			
			// 모든 게시물을 읽어서 JSON으로 내보냅니다.
			if(currentPage.equals("/listAll.do")) {
				JSONArray json = boardMgr.getListAll();
				
				response.setContentType("application/json");
				response.setCharacterEncoding("EUC-KR");
				
				PrintWriter out = response.getWriter();
				out.println(json.toJSONString());

			} else if(currentPage.equals("/postView.do")) { // 게시물 보기 
				postViewCommand.execute(request, response);
			} else if(currentPage.equals("/writeForm.do")) { // 게시물 작성
				writeCommand.execute(request, response);
				
			} else if(currentPage.equals("/deletePost.do")) { // 게시물 삭제
				DeleteCommand command = new DeleteCommand(boardMgr);
				command.execute(request, response);
			} else if(currentPage.equals("/writeReply.do")) { // 댓글 작성
				replyCommand.write(request, response);
				
			} else if(currentPage.equals("/updateReply.do")) { // 댓글 수정 
				// 댓글 업데이트
			} else if(currentPage.equals("/deleteReply.do")) { // 댓글 삭제
				// 댓글 삭제
			} else if(currentPage.equals("/imageUpload.do")) { // 이미지 업로드
				ImageUploadCommand command = new ImageUploadCommand(boardMgr, saveFolderName);
				command.execute(request, response);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

		
//		String status = (String)request.getAttribute("_status");
//		if(status.equals("error")) {
//			String errorMessage = (String)request.getAttribute("errorMessage");
//			String url = (String)request.getAttribute("url");
//			nextPage = (String)request.getAttribute("nextpage");
//			
//			System.out.println(errorMessage);
//			
//			request.removeAttribute("nextPage");
//			request.removeAttribute("_status");
//			request.removeAttribute("url");
//			
//			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
//			dispatcher.forward(request, response);
//		}
//		
	}

}
