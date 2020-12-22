package command.board.qna;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionResult;
import command.Command;

public class ViewCommand extends Command {
	public ViewCommand() {
		super();
	}
	
	@Override
	public ActionResult execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int postNumber = Integer.parseInt(request.getParameter("postNumber"));
				
		request.setAttribute("postNumber", postNumber);
		result.forward(request.getContextPath() + "/pages/board-post.jsp");
		
		return result;
	}
}
