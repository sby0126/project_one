package command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import dao.board.qna.BoardDAO;

/**
 * 명령 클래스는 request와 response 처리를 위임 받습니다.
 * 따라서 모든 Command는 이 클래스를 상속 받아야 합니다.
 */
public class Command {
	
	public ActionResult result;
	
	public Command()  {
		result = new ActionResult();
	}
		
	public BoardDAO getDAO() {
		return BoardDAO.getInstance();
	}
	
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		result.start(request, response);
		
		return result;
	}
}
