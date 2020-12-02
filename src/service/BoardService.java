package service;

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

import command.DeleteCommand;
import command.ImageUploadCommand;
import command.PostViewCommand;
import command.ReplyCommand;
import command.WriteFormCommand;
import dao.BoardDAO;

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
			} else if(currentPage.equals("/postReply.do")) { // 댓글 작성
				replyCommand.execute(request, response);
			} else if(currentPage.equals("/imageUpload.do")) { // 이미지 업로드
				ImageUploadCommand command = new ImageUploadCommand(boardMgr, saveFolderName);
				command.execute(request, response);
			} else if(currentPage.equals("/recommandPost.do")) { // 추천
				// 추천은 한 번만 가능해야 합니다. 즉, 추천인 목록이 필요합니다.
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
