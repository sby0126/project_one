package service.board.qna;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import action.ActionResult;
import action.ErrorResult;
import command.board.qna.DeleteCommand;
import command.board.qna.ImageUploadCommand;
import command.board.qna.IncreaseRecommandCountCommand;
import command.board.qna.ModifyPostFormCommand;
import command.board.qna.PostViewCommand;
import command.board.qna.ReplyCommand;
import command.board.qna.ViewCommand;
import command.board.qna.WriteFormCommand;
import dao.board.qna.BoardDAO;

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
        boardMgr = BoardDAO.getInstance();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	writeCommand = new WriteFormCommand();
    	postViewCommand = new PostViewCommand();
    	replyCommand = new ReplyCommand();
    	
    	ServletContext context = config.getServletContext();
    	
    	initWithDefaultUploadFolder( context.getRealPath("uploads") );
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
	
	/**
	 * 테이블이 존재하지 않을 경우
	 * 
	 * @return
	 */
	private boolean isNotFoundTable() {
		if(boardMgr == null) return true;
		return !boardMgr.isExistsTable();
	}
	
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String currentPage = request.getPathInfo();
		String nextPage = "pages/board-default.jsp";
		String defaultPage = "pages/board-default.jsp";
		
		ActionResult result = null;
		
		if(isNotFoundTable()) {
			result = new ErrorResult(
						"테이블이 존재하지 않거나 오류로 데이터베이스가 중단된 상태입니다. 서버를 재시작해주십시오.",
						"/pages/board-default.jsp"
					);
			result.start(request, response)			
				  .render(defaultPage);
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
				
				return;

			} else if(currentPage.equals("/search.do")) {
				
				String searchQuery = request.getParameter("searchQuery");
				if(searchQuery != null) {
					searchQuery = URLDecoder.decode(searchQuery);
				}
				JSONArray json = boardMgr.getListAllForFilter(searchQuery);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("EUC-KR");
				
				PrintWriter out = response.getWriter();
				out.println(json.toJSONString());
				
				return;				
				
			} else if(currentPage.equals("/postView.do")) { // 특정 게시물 JSON 요청
				result = postViewCommand.execute(request, response);
			} else if(currentPage.equals("/view.do")) {
				ViewCommand command = new ViewCommand();
				command.execute(request, response);
			} else if(currentPage.equals("/writeForm.do")) { // 게시물 작성
				result = writeCommand.execute(request, response);
			} else if(currentPage.equals("/deletePost.do")) { // 게시물 삭제
				DeleteCommand command = new DeleteCommand();
				result = command.execute(request, response);
			} else if(currentPage.equals("/postReply.do")) { // 댓글 작성
				result = replyCommand.execute(request, response);
			} else if(currentPage.equals("/imageUpload.do")) { // 이미지 업로드
				ImageUploadCommand command = new ImageUploadCommand(saveFolderName);
				result = command.execute(request, response);
			} else if(currentPage.equals("/modifyPost.do")) { // 글 수정 폼 채우기
				ModifyPostFormCommand command = new ModifyPostFormCommand(); 
				result = command.execute(request, response);
			} else if(currentPage.equals("/increaseRecommandCount.do")) { // 게시물 추천하기
				IncreaseRecommandCountCommand command = new IncreaseRecommandCountCommand();
				result = command.execute(request, response);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if(result != null) {
			try {
				result.render(defaultPage);	
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
